package com.example.jerye.popfilms2.remote;

import com.example.jerye.popfilms2.data.model.credits.Credits;
import com.example.jerye.popfilms2.data.model.movies.MoviesResult;
import com.example.jerye.popfilms2.data.model.review.Review;
import com.example.jerye.popfilms2.data.model.trailer.Trailer;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by jerye on 8/15/2017.
 */

public interface MoviesService {


    @GET("{type}")
    Observable<MoviesResult> getPopularMovies(
            @Path("type") String type,
            @Query("api_key") String apiKey,
            @Query("page") int page,
            @Query("language") String language);

    @GET("{id}/credits")
    Observable<Credits> getMovieCredit(@Path("id") int movieId,
                                       @Query("api_key") String apiKey,
                                       @Query("language") String language);

    @GET("{id}/reviews")
    Observable<Review> getMovieReview(@Path("id") int movieId,
                                      @Query("api_key") String apiKey,
                                      @Query("page") int page,
                                      @Query("language") String language);

    @GET("{id}/videos")
    Observable<Trailer> getMovieTrailer(@Path("id") int movieId,
                                        @Query("api_key") String apiKey, @Query("language") String language);


}
