package com.devspacecinenow

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {

    @GET("now_playing?language=en-US&page=1")
    fun getNowPLayingMovies() : Call<MovieResponse>

    @GET("top_rated?language=en-US&page=1")
    fun getTopRatedMovies() : Call<MovieResponse>

    @GET("popular?language=en-US&page=1")
    fun getPopularMovies() : Call<MovieResponse>

    @GET("upcoming?language=en-US&page=1")
    fun getUpComingMovies() : Call<MovieResponse>

    @GET("{movie_id}?language=en-US")
    fun getMoviesById(@Path("movie_id") movieId : String) : Call<MovieDto>
}