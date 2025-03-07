package com.devspacecinenow.List.presentation.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.devspacecinenow.List.presentation.MovieListViewModel



@Composable
fun MovieListScreen(
    navControler: NavHostController,
    viewModel: MovieListViewModel
) {
    val nowPlayingMovies by viewModel.uiNowPLaying.collectAsState()
    val topRatedMovies by viewModel.uiTopRated.collectAsState()
    val popularMovies by viewModel.uiPopularMovies.collectAsState()
    val upComingMovies by viewModel.uiUpComing.collectAsState()

    MovieListContent(
        popularMovies = popularMovies,
        topRatedMovies = topRatedMovies,
        nowPlayingMovies = nowPlayingMovies,
        upComingMovies = upComingMovies
    ) { itemClicked ->
        navControler.navigate(route = "movieDetail/${itemClicked.id}")
    }
}

@Composable
private fun MovieListContent(
    popularMovies: MovieListUIState,
    topRatedMovies: MovieListUIState,
    nowPlayingMovies: MovieListUIState,
    upComingMovies: MovieListUIState,
    onClick: (MovieUIData) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        Text(
            modifier = Modifier.padding(8.dp),
            fontSize = 40.sp,
            fontWeight = FontWeight.SemiBold,
            text = "CineNow"
        )
        MovieSession(
            label = "Popular",
            movieListUIState = popularMovies,
            onClick = onClick
        )


        MovieSession(
            label = "Top rated",
            movieListUIState = topRatedMovies,
            onClick = onClick
        )

        MovieSession(
            label = "Now Playing",
            movieListUIState = nowPlayingMovies,
            onClick = onClick
        )

        MovieSession(
            label = "Upcoming",
            movieListUIState = upComingMovies,
            onClick = onClick
        )
    }
}


@Composable
private fun MovieSession(
    label: String,
    movieListUIState: MovieListUIState,
    onClick: (MovieUIData) -> Unit
) {

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Text(
            fontSize = 24.sp,
            text = label,
            fontWeight = FontWeight.SemiBold
        )
        Spacer(modifier = Modifier.size(8.dp))
        if (movieListUIState.isLoading) {

        } else if (movieListUIState.isError) {
            Text(
                color = Color.Red,
                text = movieListUIState.errorMessage ?: "",
            )

        } else {
            MovieList(movieList = movieListUIState.list, onClick = onClick)
        }

    }

}

@Composable
private fun MovieList(
    movieList: List<MovieUIData>,
    onClick: (MovieUIData) -> Unit
) {
    LazyRow {
        items(movieList) {
            MovieItem(
                movieDto = it,
                onClick = onClick
            )
        }
    }
}

@Composable
private fun MovieItem(
    movieDto: MovieUIData,
    onClick: (MovieUIData) -> Unit

) {
    Column(
        modifier = Modifier
            .width(IntrinsicSize.Min)
            .clickable {
                onClick.invoke(movieDto)
            }
    ) {
        AsyncImage(
            modifier = Modifier
                .padding(4.dp)
                .width(120.dp)
                .height(150.dp),
            contentScale = ContentScale.Crop,
            model = movieDto.image,
            contentDescription = "${movieDto.title}Poster image"
        )
        Spacer(modifier = Modifier.size(4.dp))
        Text(
            fontWeight = FontWeight.SemiBold,
            overflow = TextOverflow.Ellipsis,
            maxLines = 1,
            text = movieDto.title
        )
        Text(
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            text = movieDto.overview
        )
    }
}


