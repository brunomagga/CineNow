package com.devspacecinenow

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.devspacecinenow.List.presentation.MovieListViewModel
import com.devspacecinenow.List.presentation.ui.MovieListScreen
import com.devspacecinenow.detail.presentation.MovieDetailViewModel
import com.devspacecinenow.detail.presentation.ui.MovieDetailScreen

@Composable
fun CineNowApp(
    listViewModel: MovieListViewModel,
    detailViewModel: MovieDetailViewModel
) {
    val navControler = rememberNavController()
    NavHost(navController = navControler, startDestination = "movieList"){
        composable(route = "movieList"){
            MovieListScreen(navControler, listViewModel)
        }
        composable(
            route = "movieDetail" + "/{itemId}",
            arguments = listOf(navArgument("itemId"){
                type = NavType.StringType
            })
        ){backStrackEntry ->
            val movieId = requireNotNull(backStrackEntry.arguments?.getString("itemId"))
            MovieDetailScreen(movieId, navControler, detailViewModel)
        }
    }
}