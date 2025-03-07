package com.devspacecinenow.List.data.remote

import android.accounts.NetworkErrorException
import com.devspacecinenow.common.data.local.MovieCategory
import com.devspacecinenow.common.data.model.Movie
import com.devspacecinenow.common.data.remote.model.MovieResponse

class MovieListRemoteDataSource(
    private val listService: ListService
) {
    suspend fun getNowPlaying(): Result<List<Movie>?> {
        return try {
            val response = listService.getNowPLayingMovies()
            if (response.isSuccessful) {
                val movies = response.body()?.results?.map {
                    Movie(
                        id = it.id,
                        title = it.title,
                        overview = it.overview,
                        image = it.posterFullPath,
                        category = MovieCategory.NowPlaying.name
                    )
                }
                Result.success(movies)
            } else {
                Result.failure(NetworkErrorException(response.message()))
            }
        } catch (ex: Exception) {
            ex.printStackTrace()
            Result.failure(ex)

        }
    }

    suspend fun getTopRated(): Result<List<Movie>?>  {
        return try {
            val response = listService.getTopRatedMovies()
            if (response.isSuccessful) {
                val movies = response.body()?.results?.map {
                    Movie(
                        id = it.id,
                        title = it.title,
                        overview = it.overview,
                        image = it.posterFullPath,
                        category = MovieCategory.TopRated.name
                    )
                }
                Result.success(movies)
            } else {
                Result.failure(NetworkErrorException(response.message()))
            }
        } catch (ex: Exception) {
            ex.printStackTrace()
            Result.failure(ex)
        }
    }

    suspend fun getPopular(): Result<List<Movie>?> {
        return try {
            val response = listService.getPopularMovies()
            if (response.isSuccessful) {
                val movies = response.body()?.results?.map {
                    Movie(
                        id = it.id,
                        title = it.title,
                        overview = it.overview,
                        image = it.posterFullPath,
                        category = MovieCategory.Popular.name
                    )
                }
                Result.success(movies)
            } else {
                Result.failure(NetworkErrorException(response.message()))
            }
        } catch (ex: Exception) {
            ex.printStackTrace()
            Result.failure(ex)
        }
    }

    suspend fun getUpComing(): Result<List<Movie>?> {
        return try {
            val response = listService.getUpComingMovies()
            if (response.isSuccessful) {
                val movies = response.body()?.results?.map {
                    Movie(
                        id = it.id,
                        title = it.title,
                        overview = it.overview,
                        image = it.posterFullPath,
                        category = MovieCategory.UpComing.name
                    )
                }
                Result.success(movies)
            } else {
                Result.failure(NetworkErrorException(response.message()))
            }
        } catch (ex: Exception) {
            ex.printStackTrace()
            Result.failure(ex)
        }
    }
}