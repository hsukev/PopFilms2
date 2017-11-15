package com.example.jerye.popfilms2;

import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.animation.FastOutSlowInInterpolator;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.animation.GridLayoutAnimationController;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.jerye.popfilms2.adapter.CastAdapter;
import com.example.jerye.popfilms2.adapter.MoviesAdapter;
import com.example.jerye.popfilms2.adapter.ReviewsAdapter;
import com.example.jerye.popfilms2.adapter.TrailerAdapter;
import com.example.jerye.popfilms2.data.model.GenreScheme;
import com.example.jerye.popfilms2.data.model.credits.Cast;
import com.example.jerye.popfilms2.data.model.credits.Credits;
import com.example.jerye.popfilms2.data.model.movies.Result;
import com.example.jerye.popfilms2.data.model.review.Review;
import com.example.jerye.popfilms2.data.model.trailer.Trailer;
import com.example.jerye.popfilms2.remote.MoviesService;
import com.example.jerye.popfilms2.util.Circle;
import com.example.jerye.popfilms2.util.CircleAngleAnimation;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by jerye on 8/22/2017.
 */

public class DetailedActivity extends AppCompatActivity implements CastAdapter.CastAdapterListener,
        ReviewsAdapter.ReviewsAdapterListener,
        Target {

    @BindView(R.id.detailed_background)
    ImageView background;
    @BindView(R.id.detailed_release_date)
    TextView releaseDate;
    @BindView(R.id.detailed_rating)
    Circle rating;
    @BindView(R.id.detailed_popularity)
    TextView popularity;
    @BindView(R.id.detailed_rating_number)
    TextView ratingNumber;
    @BindView(R.id.detailed_summary)
    TextView summary;
    @BindView(R.id.detailed_genre)
    TextView genreList;
    @BindView(R.id.detailed_cast)
    GridRecyclerView cast;
    @BindView(R.id.detailed_trailer)
    RecyclerView trailer;
    @BindView(R.id.detailed_reviews)
    RecyclerView reviews;
    @BindView(R.id.detailed_collapsing_toolbar)
    CollapsingToolbarLayout collapsingToolbarLayout;
    @BindView(R.id.detailed_cast_loading)
    ProgressBar castLoader;
    @BindView(R.id.detailed_cast_loading_container)
    FrameLayout castLoaderContainer;
    @BindView(R.id.detailed_cast_null)
    TextView nullCast;
    @BindView(R.id.detailed_review_null)
    TextView nullReview;
    @BindView(R.id.detailed_trailer_null)
    TextView nullTrailer;

    int languagePreference;
    Result movie;
    MoviesService castService;
    CastAdapter castAdapter;
    TrailerAdapter trailerAdapter;
    String movieTvToggle;
    ReviewsAdapter reviewsAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed);
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        languagePreference = sharedPreferences.getInt("language", 42);
        ButterKnife.bind(this);
    }


    @Override
    public void onClick(com.example.jerye.popfilms2.data.model.review.Result fullReview) {
        Log.d("ReviewAdapter", "clicked");
        ReviewDialog reviewDialog = ReviewDialog.newInstance(fullReview);
        reviewDialog.show(getSupportFragmentManager(), "full review");
    }

    @Override
    protected void onStart() {
        super.onStart();
        movie = retrieveMovie();
        populateViews();
        setUpNetwork();
    }

    public Result retrieveMovie() {
        Bundle bundle = getIntent().getBundleExtra(MoviesAdapter.INTENT_KEY);
        movieTvToggle = bundle.getString("movieTvToggle", "movie");
        return (Result) bundle.getSerializable(MoviesAdapter.BUNDLE_KEY);
    }

    @Override
    public void onComplete() {
        castLoader.setVisibility(View.GONE);
        cast.setVisibility(View.VISIBLE);
        cast.scheduleLayoutAnimation();
    }

    public void populateViews() {
        Picasso.with(this).load("http://image.tmdb.org/t/p/w500/" + movie.getBackdropPath()).into(this);
        collapsingToolbarLayout.setTitle(movieTvToggle.equals("movie") ? movie.getTitle() : movie.getName());
        // Converts date to proper format
        SimpleDateFormat readFormat = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat writeFormat = new SimpleDateFormat("MMM.\ndd\nyyyy");
        Date date;
        StringBuilder sb = new StringBuilder();

        String dateData = movieTvToggle.equals("tv") ? movie.getFirstAirDate() : movie.getReleaseDate();
        try {
            date = readFormat.parse(dateData);
            sb.append(writeFormat.format(date));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        releaseDate.setText(sb.toString());
        summary.setText(movie.getOverview());
        Log.d("DetailedActivity", movie.getOverview());
        genreList.setText(GenreScheme.getGenre(movie.getGenreIds(), movieTvToggle.equals("movie")), TextView.BufferType.SPANNABLE);
        ratingNumber.setText(String.valueOf(movie.getVoteAverage()));
        popularity.setText(String.valueOf(Math.round(movie.getPopularity())));

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 1, LinearLayoutManager.HORIZONTAL, false);
        GridLayoutAnimationController controller = (GridLayoutAnimationController) cast.getLayoutAnimation();
        controller.setDirection(R.anim.cast_animation);
        cast.setLayoutManager(gridLayoutManager);
        cast.setLayoutAnimation(controller);
        castAdapter = new CastAdapter(this, this);
        cast.setAdapter(castAdapter);

        LinearLayoutManager linearLayoutManagerTrailer = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        trailer.setLayoutManager(linearLayoutManagerTrailer);
        trailerAdapter = new TrailerAdapter(this);
        trailer.setAdapter(trailerAdapter);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        reviews.setLayoutManager(linearLayoutManager);
        reviewsAdapter = new ReviewsAdapter(this, this);
        reviews.setAdapter(reviewsAdapter);

        float ratingAngle = movie.getVoteAverage() * 360 / 10;
        CircleAngleAnimation circleAngleAnimation = new CircleAngleAnimation(rating, ratingAngle);
        circleAngleAnimation.setDuration(1500);
        circleAngleAnimation.setInterpolator(new FastOutSlowInInterpolator());
        rating.startAnimation(circleAngleAnimation);
    }

    public void setUpNetwork() {

        Retrofit retrofitClient = new Retrofit.Builder()
                .baseUrl("https://api.themoviedb.org/3/")
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        castService = retrofitClient.create(MoviesService.class);
        fetchData();

    }

    public void fetchData() {
        Log.d("Cast", movieTvToggle + movie.getId() + BuildConfig.TMDB_API_KEY);

        Observable<Credits> creditsResult = castService.getMovieCredit(movieTvToggle, movie.getId(), BuildConfig.TMDB_API_KEY);
        creditsResult
                .subscribeOn(Schedulers.io())
                .flatMap(credits2Cast())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableObserver<Cast>() {
                    int limit = 15;

                    @Override
                    public void onNext(@NonNull Cast cast) {
                        limit--;
                        if (limit > 0) castAdapter.addCast(cast);
                        Log.d("Cast", "Add");
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.d("Cast", "Error:" + e);
                        nullCast.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onComplete() {
                        castLoaderContainer.setVisibility(View.GONE);
                        castAdapter.fetchingComplete();
                    }
                });
        Observable<Review> reviewsResult = castService.getMovieReview(movieTvToggle, movie.getId(), BuildConfig.TMDB_API_KEY, 1);
        Log.d("Review", reviewsResult.toString());
        reviewsResult
                .subscribeOn(Schedulers.io())
                .flatMap(review2Result())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableObserver<com.example.jerye.popfilms2.data.model.review.Result>() {
                    @Override
                    public void onNext(@NonNull com.example.jerye.popfilms2.data.model.review.Result result) {
                        reviewsAdapter.addReviews(result);
                        Log.d("Review", "added");
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.d("Review", "Error: " + e);
                        nullReview.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onComplete() {
                        Log.d("Review", "completed");
                    }
                });

        Observable<Trailer> trailerResult = castService.getMovieTrailer(movieTvToggle, movie.getId(), BuildConfig.TMDB_API_KEY);
        trailerResult
                .subscribeOn(Schedulers.io())
                .flatMap(trailer2Result())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableObserver<com.example.jerye.popfilms2.data.model.trailer.Result>() {
                    @Override
                    public void onNext(@NonNull com.example.jerye.popfilms2.data.model.trailer.Result result) {
                        trailerAdapter.addTrailer(result);
                        Log.d("Trailer", "added");
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.d("Trailer", "Error: " + e);
                        nullTrailer.setVisibility(View.VISIBLE);

                    }

                    @Override
                    public void onComplete() {
                        Log.d("Trailer", "completed");
                    }
                });

    }

    public Function<Credits, Observable<Cast>> credits2Cast() {
        return new Function<Credits, Observable<Cast>>() {
            @Override
            public Observable<Cast> apply(@NonNull Credits credits) throws Exception {
                return Observable.fromIterable(credits.getCast());
            }
        };
    }

    public Function<Review, Observable<com.example.jerye.popfilms2.data.model.review.Result>> review2Result() {
        return new Function<Review, Observable<com.example.jerye.popfilms2.data.model.review.Result>>() {
            @Override
            public Observable<com.example.jerye.popfilms2.data.model.review.Result> apply(@NonNull Review review) throws Exception {
                return Observable.fromIterable(review.getResults());
            }
        };
    }

    public Function<Trailer, Observable<com.example.jerye.popfilms2.data.model.trailer.Result>> trailer2Result() {
        return new Function<Trailer, Observable<com.example.jerye.popfilms2.data.model.trailer.Result>>() {
            @Override
            public Observable<com.example.jerye.popfilms2.data.model.trailer.Result> apply(@NonNull Trailer trailer) throws Exception {
                return Observable.fromIterable(trailer.getResults());
            }
        };
    }

    //For Picasso bitmap loading muted color scrim
    @Override
    public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
        Palette.Builder paletteBuilder = Palette.from(bitmap);
        int mutedColor = paletteBuilder.generate().getDarkVibrantColor(0xFF333333);

        mutedColor = Color.argb(Math.round(Color.alpha(mutedColor) * 0.8f),
                Color.red(mutedColor),
                Color.green(mutedColor),
                Color.blue(mutedColor)
        );
        collapsingToolbarLayout.setContentScrimColor(mutedColor);
        collapsingToolbarLayout.setStatusBarScrimColor(mutedColor);
        collapsingToolbarLayout.setExpandedTitleTextAppearance(R.style.AppTheme_ExpandedTitle);
        collapsingToolbarLayout.setCollapsedTitleTextColor(ContextCompat.getColor(this, R.color.light_text));
        background.setImageBitmap(bitmap);
    }

    @Override
    public void onBitmapFailed(Drawable errorDrawable) {

    }

    @Override
    public void onPrepareLoad(Drawable placeHolderDrawable) {

    }

    public int colorDistinction(int muted) {
        return muted;
    }


}
