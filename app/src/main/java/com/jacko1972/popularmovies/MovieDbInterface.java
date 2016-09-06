package com.jacko1972.popularmovies;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;


public interface MovieDbInterface {

    @GET("{movie_list}")
    Call<FullMovieJsonResponse> getMovieByListType(@Path("movie_list") String movie_list, @Query("api_key") String api_key);

}
