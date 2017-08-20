package com.example.jerye.popfilms2;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.example.jerye.popfilms2.adapter.MoviesAdapter;
import com.example.jerye.popfilms2.adapter.MoviesItemAnimator;
import com.example.jerye.popfilms2.data.model.MoviesResult;
import com.example.jerye.popfilms2.data.model.Result;
import com.example.jerye.popfilms2.remote.MoviesService;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.annotations.Nullable;
import io.reactivex.functions.Function;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    @Nullable
    @BindView(R.id.rv_main)
    RecyclerView rvMain;

    MoviesAdapter moviesAdapter;
    String TAG = "MainActivity.java";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        setUpGrid();
        setUpNetwork();

    }

    private void setUpGrid() {
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2, LinearLayoutManager.VERTICAL, false);

        moviesAdapter = new MoviesAdapter(this);

        rvMain.setAdapter(moviesAdapter);
        rvMain.setLayoutManager(gridLayoutManager);
        rvMain.setItemAnimator(new MoviesItemAnimator());

    }

    public void setUpNetwork() {
        Retrofit retrofitClient = new Retrofit.Builder()
                .baseUrl("https://api.themoviedb.org/3/movie/")
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        MoviesService moviesService = retrofitClient.create(MoviesService.class);
        Observable<MoviesResult> moviesResult = moviesService.getPopularMovies(BuildConfig.TMDB_API_KEY);
        moviesResult
                .subscribeOn(Schedulers.io())
                .flatMap(moviesResult2Result())
                .map(result2Info())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableObserver<String>() {
                    @Override
                    public void onNext(@NonNull String s) {
                        moviesAdapter.addMovies(s);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.d(TAG, "error: " + e);

                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "complete");
                    }
                });

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
