package com.urbanutility.jerye.popfilms2.remote;

import com.urbanutility.jerye.popfilms2.data.model.credits.Credits;
import com.urbanutility.jerye.popfilms2.data.model.movies.MoviesResult;
import com.urbanutility.jerye.popfilms2.data.model.review.Review;
import com.urbanutility.jerye.popfilms2.data.model.trailer.Trailer;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by jerye on 8/15/2017.
 */

public interface MoviesService {


    @GET("{tvToggle}/{filterType}")
    Observable<MoviesResult> getPopularMovies(@Path("tvToggle") String tvToggle,
                                              @Path("filterType") String type,
                                              @Query("api_key") String apiKey,
                                              @Query("page") int page);

    @GET("{tvToggle}/{id}/credits")
    Observable<Credits> getMovieCredit(@Path("tvToggle") String tvToggle,
                                       @Path("id") int movieId,
                                       @Query("api_key") String apiKey
                                       );

    @GET("{tvToggle}/{id}/reviews")
    Observable<Review> getMovieReview(@Path("tvToggle") String tvToggle,
                                      @Path("id") int movieId,
                                      @Query("api_key") String apiKey,
                                      @Query("page") int page);

    @GET("{tvToggle}/{id}/videos")
    Observable<Trailer> getMovieTrailer(@Path("tvToggle") String tvToggle,
                                        @Path("id") int movieId,
                                        @Query("api_key") String apiKey);


}
