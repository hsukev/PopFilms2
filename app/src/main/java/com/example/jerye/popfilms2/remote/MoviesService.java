package com.example.jerye.popfilms2.remote;

import com.example.jerye.popfilms2.data.model.MoviesResult;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by jerye on 8/15/2017.
 */

public interface MoviesService {


    @GET("popular")
    Observable<MoviesResult> getPopularMovies(@Query("api_key") String apiKey,
                                              @Query("page") int page);

    @GET("top_rated")
    Observable<MoviesResult> getTopRatedMovies(@Query("api_key") String apiKey,
                                               @Query("page") int page);

}
