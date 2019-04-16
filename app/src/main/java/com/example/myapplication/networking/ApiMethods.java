package com.example.myapplication.networking;


import com.example.myapplication.model.MoviesDetailResponse;
import com.example.myapplication.model.MoviesListResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;



public interface ApiMethods {

    @GET(AppConstants.HTTP.LIST_URL)
    Call<BaseResponse<List<MoviesListResponse>>> getMoviesList(
            @Query("api_key") String apiKey
    );

    @GET(AppConstants.HTTP.DETAIL_URL+"{movie_id}")
    Call<MoviesDetailResponse> getMovieDetails(
            @Path("movie_id") int movieId,
            @Query("api_key") String apiKey,
            @Query("language") String language
    );

}
