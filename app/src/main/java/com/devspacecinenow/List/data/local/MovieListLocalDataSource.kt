package com.devspacecinenow.List.data.local

import com.devspacecinenow.common.data.local.MovieCategory
import com.devspacecinenow.common.data.local.MovieDao
import com.devspacecinenow.common.data.local.MovieEntity
import com.devspacecinenow.common.data.model.Movie

class MovieListLocalDataSource(
    private val dao: MovieDao
) {

    suspend fun getNowPlayingMovies(): List<Movie> {
        return getMoviesByCategory(MovieCategory.NowPlaying)
    }

    suspend fun getTopRatedMovies(): List<Movie> {
        return getMoviesByCategory(MovieCategory.TopRated)
    }

    suspend fun getPopularMovies(): List<Movie> {
        return getMoviesByCategory(MovieCategory.Popular)
    }

    suspend fun getUpComingMovies(): List<Movie> {
        return getMoviesByCategory(MovieCategory.UpComing)
    }

    suspend fun updateLocalItems(movies: List<Movie>){
        val entities = movies.map{
            MovieEntity(
                id = it.id,
                title = it.title,
                overview = it.overview,
                image = it.image,
                category = it.category
            )
        }
        dao.insertAll(entities)
    }

    private suspend fun getMoviesByCategory(movieCategory: MovieCategory): List<Movie> {
        val entities = dao.getMoviesBYCategory(movieCategory.name)

        return entities.map {
            Movie(
                id = it.id,
                title = it.title,
                overview = it.overview,
                image = it.image,
                category = it.category
            )
        }
    }
}