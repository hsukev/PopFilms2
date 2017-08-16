package com.example.jerye.popfilms2;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.jerye.popfilms2.data.model.MoviesResult;
import com.example.jerye.popfilms2.remote.MoviesService;

import java.util.function.Function;

import butterknife.BindView;
import io.reactivex.Observable;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity{

    @BindView(R.id.rv_main)
    RecyclerView rvMain;

    MoviesAdapter moviesAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    private void setUpGrid(){
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this,2, LinearLayoutManager.VERTICAL,false);

        moviesAdapter = new MoviesAdapter(this);

        rvMain.setAdapter(moviesAdapter);
        rvMain.setLayoutManager(gridLayoutManager);

    }

    public void setUpNetwork(){
        Retrofit retrofitClient = new Retrofit.Builder()
                .baseUrl("https://api.themoviedb.org/3/movie")
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        MoviesService moviesService = retrofitClient.create(MoviesService.class);
        Observable<MoviesResult> moviesResult = moviesService.getPopularMovies(BuildConfig.TMDB_API_KEY);


    }
}
