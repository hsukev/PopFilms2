package com.example.jerye.popfilms2;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.example.jerye.popfilms2.adapter.MovieScrollListener;
import com.example.jerye.popfilms2.adapter.MoviesAdapter;
import com.example.jerye.popfilms2.data.model.movies.MoviesResult;
import com.example.jerye.popfilms2.data.model.movies.Result;
import com.example.jerye.popfilms2.remote.MoviesService;

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
 * A simple {@link Fragment} subclass.
 */
public class MoviesFragment extends Fragment implements MoviesAdapter.MovieAdapterListener,
        MovieScrollListener.ReloadListener {
    @BindView(R.id.rv_main)
    GridRecyclerView rvMain;
    @BindView(R.id.load_screen)
    ProgressBar loadBar;

    MoviesAdapter moviesAdapter;
    MoviesService moviesService;
    int page = 1;
    String queryType;

    public static MoviesFragment newInstance(String queryType) {
        Bundle args = new Bundle();
        args.putString("query_type", queryType);
        MoviesFragment fragment = new MoviesFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public MoviesFragment() {
        // Required empty public constructor
    }


    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.main_template, container, false);
        ButterKnife.bind(this, view);
        queryType = this.getArguments().getString("query_type");


        setUpGrid();
        setUpNetwork();
        fetchMovieData(1);

        return view;
    }

    private void setUpGrid() {
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2, LinearLayoutManager.VERTICAL, false);

        moviesAdapter = new MoviesAdapter(getContext(), this);

        rvMain.setLayoutManager(gridLayoutManager);
        rvMain.addOnScrollListener(new MovieScrollListener(gridLayoutManager, this));
        rvMain.setAdapter(moviesAdapter);
    }

    @Override
    public void loadMore() {
        page++;
        fetchMovieData(page);
    }

    @Override
    public void onComplete() {
        loadBar.setVisibility(View.GONE);
        rvMain.scheduleLayoutAnimation();

    }

    public void setUpNetwork() {

        Retrofit retrofitClient = new Retrofit.Builder()
                .baseUrl("https://api.themoviedb.org/3/movie/")
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        moviesService = retrofitClient.create(MoviesService.class);
    }

    public void fetchMovieData(int page) {
        Observable<MoviesResult> moviesResult = moviesService.getPopularMovies(this.queryType,BuildConfig.TMDB_API_KEY, page);
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
                        Log.d("MoviesFragment", "error: " + e);
                    }

                    @Override
                    public void onComplete() {
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
