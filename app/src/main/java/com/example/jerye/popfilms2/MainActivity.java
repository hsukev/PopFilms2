package com.example.jerye.popfilms2;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.ProgressBar;

import com.example.jerye.popfilms2.adapter.MovieScrollListener;
import com.example.jerye.popfilms2.adapter.MoviesAdapter;
import com.example.jerye.popfilms2.data.model.MoviesResult;
import com.example.jerye.popfilms2.data.model.Result;
import com.example.jerye.popfilms2.remote.MoviesService;
import com.example.jerye.popfilms2.util.Utils;

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

public class MainActivity extends BaseActivity implements MoviesAdapter.MovieAdapterListener,
        MovieScrollListener.ReloadListener{


    @BindView(R.id.rv_main)
    GridRecyclerView rvMain;
    @BindView(R.id.load_screen)
    ProgressBar loadBar;


    MoviesAdapter moviesAdapter;
    MoviesService moviesService;
    String TAG = "MainActivity.java";

    int page = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        startUpAnimation();
        setUpGrid();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        Log.d(TAG, "options menu");
        return true;

    }

    @Override
    public void onEnterAnimationComplete() {
        super.onEnterAnimationComplete();
        Log.d("test", "onEnterAnimation");
        setUpNetwork();

    }

    @Override
    public void onComplete() {
        loadBar.setVisibility(View.GONE);
        rvMain.scheduleLayoutAnimation();

    }


    @Override
    public void loadMore() {
        page++;
        fetchMovieData(page);
    }

    private void setUpGrid() {
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2, LinearLayoutManager.VERTICAL, false);

        moviesAdapter = new MoviesAdapter(this, this);

        rvMain.setLayoutManager(gridLayoutManager);
        rvMain.addOnScrollListener(new MovieScrollListener(gridLayoutManager, this));
        rvMain.setAdapter(moviesAdapter);
    }

    public void setUpNetwork() {

        Retrofit retrofitClient = new Retrofit.Builder()
                .baseUrl("https://api.themoviedb.org/3/movie/")
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        moviesService = retrofitClient.create(MoviesService.class);
        fetchMovieData(1);

    }

    public void fetchMovieData(int page){
        Observable<MoviesResult> moviesResult = moviesService.getPopularMovies(BuildConfig.TMDB_API_KEY, page);
        moviesResult
                .subscribeOn(Schedulers.io())
                .flatMap(moviesResult2Result())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableObserver<Result>() {
                    @Override
                    public void onNext(@NonNull Result r) {
                        moviesAdapter.addMovies(r);
                    }
                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.d(TAG, "error: " + e);
                    }
                    @Override
                    public void onComplete() {
                    }
                });
    }

    public void startUpAnimation() {
        int height = Utils.dpToPx(56);
        getToolbar().setTranslationY(-height);
        getAppTitle().setTranslationY(-height);
        getBox1().setTranslationY(-height);
        getBox2().setTranslationY(-height);

        getToolbar().animate()
                .translationY(0)
                .setDuration(300)
                .setStartDelay(300);

        getAppTitle().animate()
                .translationY(0)
                .setDuration(300)
                .setStartDelay(400);
        getBox1().animate()
                .translationY(0)
                .setDuration(300)
                .setStartDelay(500);

        getBox2().animate()
                .translationY(0)
                .setDuration(300)
                .setStartDelay(600)
                .start();
    }

    public Function<MoviesResult, Observable<Result>> moviesResult2Result() {
        return new Function<MoviesResult, Observable<Result>>() {
            @Override
            public Observable<Result> apply(@NonNull MoviesResult moviesResult) throws Exception {
                return Observable.fromIterable(moviesResult.getResults());
            }
        };

    }

    public Function<Result, String> result2Info() {
        return new Function<Result, String>() {
            @Override
            public String apply(@NonNull Result result) throws Exception {
                return result.getPosterPath();
            }
        };
    }


}
