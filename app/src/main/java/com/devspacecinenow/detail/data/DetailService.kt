package com.devspacecinenow.detail.data

import com.devspacecinenow.common.model.MovieDto
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface DetailService {
    @GET("{movie_id}?language=en-US")
    fun getMoviesById(@Path("movie_id") movieId : String) : Call<MovieDto>
}