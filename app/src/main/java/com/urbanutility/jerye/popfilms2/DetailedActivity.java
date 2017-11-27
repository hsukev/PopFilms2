package com.urbanutility.jerye.popfilms2;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.animation.FastOutSlowInInterpolator;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.animation.GridLayoutAnimationController;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubeIntents;
import com.google.android.youtube.player.YouTubePlayer;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;
import com.urbanutility.jerye.popfilms2.adapter.CastAdapter;
import com.urbanutility.jerye.popfilms2.adapter.MoviesAdapter;
import com.urbanutility.jerye.popfilms2.adapter.ReviewsAdapter;
import com.urbanutility.jerye.popfilms2.adapter.TrailerAdapter;
import com.urbanutility.jerye.popfilms2.data.model.GenreScheme;
import com.urbanutility.jerye.popfilms2.data.model.credits.Cast;
import com.urbanutility.jerye.popfilms2.data.model.credits.Credits;
import com.urbanutility.jerye.popfilms2.data.model.movies.Result;
import com.urbanutility.jerye.popfilms2.data.model.review.Review;
import com.urbanutility.jerye.popfilms2.data.model.trailer.Trailer;
import com.urbanutility.jerye.popfilms2.remote.MoviesService;
import com.urbanutility.jerye.popfilms2.util.Circle;
import com.urbanutility.jerye.popfilms2.util.CircleAngleAnimation;

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
        TrailerAdapter.TrailerAdapterListener,
        YouTubePlayer.OnInitializedListener,
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

    Result movie;
    MoviesService castService;
    CastAdapter castAdapter;
    TrailerAdapter trailerAdapter;
    String movieTvToggle, tvString = "tv", movieString = "movie", fullReviewString = "full review";
    String baseImageUrl = "http://image.tmdb.org/t/p/w500/", baseTMDBUrl = "https://api.themoviedb.org/3/";
    String dateFormatRead = "yyyy-MM-dd", dateFormatWrite = "MMM.\ndd\nyyyy";
    ReviewsAdapter reviewsAdapter;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed);
        ButterKnife.bind(this);
    }


    @Override
    public void onClick(com.urbanutility.jerye.popfilms2.data.model.review.Result fullReview) {
        ReviewDialog reviewDialog = ReviewDialog.newInstance(fullReview);
        reviewDialog.show(getSupportFragmentManager(), fullReviewString);
    }

    @Override
    public void onTrailerClick(String youtubeId) {
        Intent youTubeIntents = YouTubeIntents.createPlayVideoIntentWithOptions(this, youtubeId, false, true);
        startActivity(youTubeIntents);
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
        movieTvToggle = bundle.getString("movieTvToggle", movieString);
        return (Result) bundle.getSerializable(MoviesAdapter.BUNDLE_KEY);
    }

    @Override
    public void onComplete() {
        castLoader.setVisibility(View.GONE);
        cast.setVisibility(View.VISIBLE);
        cast.scheduleLayoutAnimation();
    }

    public void populateViews() {
        Picasso.with(this).load(baseImageUrl + movie.getBackdropPath()).into(this);
        collapsingToolbarLayout.setTitle(movieTvToggle.equals(movieString) ? movie.getTitle() : movie.getName());
        // Converts date to proper format
        SimpleDateFormat readFormat = new SimpleDateFormat(dateFormatRead);
        SimpleDateFormat writeFormat = new SimpleDateFormat(dateFormatWrite);
        Date date;
        StringBuilder sb = new StringBuilder();

        String dateData = movieTvToggle.equals(tvString) ? movie.getFirstAirDate() : movie.getReleaseDate();
        try {
            date = readFormat.parse(dateData);
            sb.append(writeFormat.format(date));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        releaseDate.setText(sb.toString());
        summary.setText(movie.getOverview());
        genreList.setText(GenreScheme.getGenre(movie.getGenreIds(), movieTvToggle.equals(movieString)), TextView.BufferType.SPANNABLE);
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
        trailerAdapter = new TrailerAdapter(this, this);
        trailer.setAdapter(trailerAdapter);

        collapsingToolbarLayout.setExpandedTitleTextAppearance(R.style.AppTheme_ExpandedTitle);
        collapsingToolbarLayout.setCollapsedTitleTextColor(ContextCompat.getColor(this, R.color.light_text));

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
                .baseUrl(baseTMDBUrl)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        castService = retrofitClient.create(MoviesService.class);
        fetchData();

    }

    public void fetchData() {
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
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        nullCast.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onComplete() {
                        castLoaderContainer.setVisibility(View.GONE);
                        castAdapter.fetchingComplete();
                        if (castAdapter.getItemCount() == 0) nullCast.setVisibility(View.VISIBLE);

                    }
                });
        Observable<Review> reviewsResult = castService.getMovieReview(movieTvToggle, movie.getId(), BuildConfig.TMDB_API_KEY, 1);
        reviewsResult
                .subscribeOn(Schedulers.io())
                .flatMap(review2Result())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableObserver<com.urbanutility.jerye.popfilms2.data.model.review.Result>() {
                    @Override
                    public void onNext(@NonNull com.urbanutility.jerye.popfilms2.data.model.review.Result result) {
                        reviewsAdapter.addReviews(result);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        nullReview.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onComplete() {
                        if (reviewsAdapter.getItemCount() == 0)
                            nullReview.setVisibility(View.VISIBLE);

                    }
                });

        Observable<Trailer> trailerResult = castService.getMovieTrailer(movieTvToggle, movie.getId(), BuildConfig.TMDB_API_KEY);
        trailerResult
                .subscribeOn(Schedulers.io())
                .flatMap(trailer2Result())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableObserver<com.urbanutility.jerye.popfilms2.data.model.trailer.Result>() {
                    @Override
                    public void onNext(@NonNull com.urbanutility.jerye.popfilms2.data.model.trailer.Result result) {
                        trailerAdapter.addTrailer(result);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        nullTrailer.setVisibility(View.VISIBLE);

                    }

                    @Override
                    public void onComplete() {
                        if (trailerAdapter.getItemCount() == 0)
                            nullTrailer.setVisibility(View.VISIBLE);
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

    public Function<Review, Observable<com.urbanutility.jerye.popfilms2.data.model.review.Result>> review2Result() {
        return new Function<Review, Observable<com.urbanutility.jerye.popfilms2.data.model.review.Result>>() {
            @Override
            public Observable<com.urbanutility.jerye.popfilms2.data.model.review.Result> apply(@NonNull Review review) throws Exception {
                return Observable.fromIterable(review.getResults());
            }
        };
    }

    public Function<Trailer, Observable<com.urbanutility.jerye.popfilms2.data.model.trailer.Result>> trailer2Result() {
        return new Function<Trailer, Observable<com.urbanutility.jerye.popfilms2.data.model.trailer.Result>>() {
            @Override
            public Observable<com.urbanutility.jerye.popfilms2.data.model.trailer.Result> apply(@NonNull Trailer trailer) throws Exception {
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
        background.setImageBitmap(bitmap);
    }

    @Override
    public void onBitmapFailed(Drawable errorDrawable) {

    }

    @Override
    public void onPrepareLoad(Drawable placeHolderDrawable) {
        background.setImageDrawable(placeHolderDrawable);
    }

    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
        youTubePlayer.play();
    }

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {

    }
}
