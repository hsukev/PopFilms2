package com.example.jerye.popfilms2;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.animation.FastOutSlowInInterpolator;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.jerye.popfilms2.adapter.CastAdapter;
import com.example.jerye.popfilms2.adapter.MoviesAdapter;
import com.example.jerye.popfilms2.data.model.Cast;
import com.example.jerye.popfilms2.data.model.Credits;
import com.example.jerye.popfilms2.data.model.GenreScheme;
import com.example.jerye.popfilms2.data.model.Result;
import com.example.jerye.popfilms2.remote.MoviesService;
import com.example.jerye.popfilms2.util.Circle;
import com.example.jerye.popfilms2.util.CircleAngleAnimation;
import com.squareup.picasso.Picasso;

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

public class DetailedActivity extends AppCompatActivity {
    @BindView(R.id.detailed_background)
    ImageView background;
    @BindView(R.id.detailed_title)
    TextView title;
    @BindView(R.id.detailed_release_date)
    TextView releaseDate;
    @BindView(R.id.detailed_rating)
    Circle rating;
    @BindView(R.id.detailed_summary)
    TextView summary;
    @BindView(R.id.detailed_genre)
    TextView genreList;
    @BindView(R.id.detailed_cast)
    GridRecyclerView cast;

    Result movie;
    MoviesService castService;
    CastAdapter castAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed);
        ButterKnife.bind(this);
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
        return (Result) bundle.getSerializable(MoviesAdapter.BUNDLE_KEY);
    }

    public void populateViews() {
        Picasso.with(this).load("http://image.tmdb.org/t/p/w500/" + movie.getBackdropPath()).into(background);
        title.setText(movie.getOriginalTitle());
        releaseDate.setText(movie.getReleaseDate());
        summary.setText(movie.getOverview());
        genreList.setText(GenreScheme.getGenre(movie.getGenreIds()));

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 1, LinearLayoutManager.HORIZONTAL,false);
        cast.setLayoutManager(gridLayoutManager);
        castAdapter = new CastAdapter(this);
        cast.setAdapter(castAdapter);

        float ratingAngle = movie.getVoteAverage() * 360 / 10;
        CircleAngleAnimation circleAngleAnimation = new CircleAngleAnimation(rating, ratingAngle);
        circleAngleAnimation.setDuration(3000);
        circleAngleAnimation.setInterpolator(new FastOutSlowInInterpolator());
        rating.startAnimation(circleAngleAnimation);
    }

    public void setUpNetwork() {

        Retrofit retrofitClient = new Retrofit.Builder()
                .baseUrl("https://api.themoviedb.org/3/movie/")
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        castService = retrofitClient.create(MoviesService.class);
        fetchCastData();

    }

    public void fetchCastData(){
        Observable<Credits> moviesResult = castService.getMovieCredit(movie.getId(),BuildConfig.TMDB_API_KEY);
        moviesResult
                .subscribeOn(Schedulers.io())
                .flatMap(credits2Cast())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableObserver<Cast>() {
                    @Override
                    public void onNext(@NonNull Cast cast) {
                        castAdapter.addCast(cast);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    public Function<Credits, Observable<Cast>> credits2Cast(){
        return new Function<Credits, Observable<Cast>>() {
            @Override
            public Observable<Cast> apply(@NonNull Credits credits) throws Exception {
                return Observable.fromIterable(credits.getCast());
            }
        };
    }



}
