package com.devspacecinenow.List.presentation


import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import com.devspacecinenow.CineNowApplication
import com.devspacecinenow.List.data.MovieListRepository
import com.devspacecinenow.List.presentation.ui.MovieListUIState
import com.devspacecinenow.List.presentation.ui.MovieUIData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

import java.net.UnknownHostException

class MovieListViewModel(
    private val repository: MovieListRepository
) : ViewModel() {

    private val _uiNowPlaying = MutableStateFlow(MovieListUIState())
    val uiNowPLaying: StateFlow<MovieListUIState> = _uiNowPlaying

    private val _uiTopRated = MutableStateFlow(MovieListUIState())
    val uiTopRated: StateFlow<MovieListUIState> = _uiTopRated

    private val _uiPopularMovies = MutableStateFlow(MovieListUIState())
    val uiPopularMovies: StateFlow<MovieListUIState> = _uiPopularMovies

    private val _uiUpComing = MutableStateFlow(MovieListUIState())
    val uiUpComing: StateFlow<MovieListUIState> = _uiUpComing

    init {
        fetchGetNowPlayingMovies()

        fetchTopRatedMovies()

        fetchGetPopularMovies()

        fetchUpComingMovies()
    }

    private fun fetchGetNowPlayingMovies() {
        _uiNowPlaying.value = MovieListUIState(isLoading = true)
        viewModelScope.launch(Dispatchers.IO) {

            val result = repository.getNowPlaying()
            if (result.isSuccess) {
                val movies = result.getOrNull()
                if (movies != null) {
                    val movieUIDataList = movies.map { movieDto ->
                        MovieUIData(
                            id = movieDto.id,
                            title = movieDto.title,
                            overview = movieDto.overview,
                            image = movieDto.image
                        )
                    }
                    _uiNowPlaying.value = MovieListUIState(list = movieUIDataList)
                }
            } else {
                val ex = result.exceptionOrNull()
                if (ex is UnknownHostException) {
                    _uiNowPlaying.value = MovieListUIState(
                        isError = true,
                        errorMessage = "No internet connection"
                    )
                } else {
                    _uiNowPlaying.value = MovieListUIState(isError = true)
                }
            }
        }
    }

    private fun fetchTopRatedMovies() {
        _uiTopRated.value = MovieListUIState(isLoading = true)
        viewModelScope.launch(Dispatchers.IO) {

            val response = repository.getTopRated()
            if (response.isSuccess) {
                val movies = response.getOrNull()?.results
                if (movies != null) {
                    val movieUIDataList = movies.map { movieDto ->
                        MovieUIData(
                            id = movieDto.id,
                            title = movieDto.title,
                            overview = movieDto.overview,
                            image = movieDto.posterFullPath
                        )
                    }
                    _uiTopRated.value = MovieListUIState(list = movieUIDataList)
                }
            } else {
                val ex = response.exceptionOrNull()
                if (ex is UnknownHostException) {
                    _uiTopRated.value = MovieListUIState(
                        isError = true,
                        errorMessage = "No internet connection"
                    )
                } else {
                    _uiTopRated.value = MovieListUIState(isError = true)
                }
            }
        }
    }

    private fun fetchGetPopularMovies() {
        _uiPopularMovies.value = MovieListUIState(isLoading = true)
        viewModelScope.launch(Dispatchers.IO) {

            val response = repository.getPopular()
            if (response.isSuccess) {
                val movies = response.getOrNull()?.results
                if (movies != null) {
                    val movieListUIData = movies.map { movieDto ->
                        MovieUIData(
                            id = movieDto.id,
                            title = movieDto.title,
                            overview = movieDto.overview,
                            image = movieDto.posterFullPath
                        )
                    }
                    _uiPopularMovies.value = MovieListUIState(list = movieListUIData)
                }
            } else {
                val ex = response.exceptionOrNull()
                if (ex is UnknownHostException) {
                    _uiPopularMovies.value = MovieListUIState(
                        isError = true,
                        errorMessage = "No internet connection"
                    )
                } else {
                    _uiPopularMovies.value = MovieListUIState(isError = true)
                }
            }
        }
    }

    private fun fetchUpComingMovies() {
        _uiUpComing.value = MovieListUIState(isLoading = true)
        viewModelScope.launch(Dispatchers.IO) {
            val response = repository.getUpComing()
            if (response.isSuccess) {
                val movies = response.getOrNull()?.results
                if (movies != null) {
                    val movieListUIData = movies.map { movieDto ->
                        MovieUIData(
                            id = movieDto.id,
                            title = movieDto.title,
                            overview = movieDto.overview,
                            image = movieDto.posterFullPath
                        )
                    }
                    _uiUpComing.value = MovieListUIState(list = movieListUIData)
                }
            } else {
                val ex = response.exceptionOrNull()
                if (ex is UnknownHostException) {
                    _uiUpComing.value = MovieListUIState(
                        isError = true,
                        errorMessage = "No internet connection"
                    )
                } else {
                    _uiUpComing.value = MovieListUIState(isError = true)
                }
            }
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
                val application = checkNotNull(extras[APPLICATION_KEY])
                return MovieListViewModel(
                    repository = (application as CineNowApplication).repository
                ) as T
            }
        }
    }
}
