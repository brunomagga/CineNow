package com.devspacecinenow

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.devspacecinenow.List.presentation.ui.MovieListScreen
import com.devspacecinenow.detail.presentation.MovieDetailScreen

@Composable
fun CineNowApp() {
    val navControler = rememberNavController()
    NavHost(navController = navControler, startDestination = "movieList"){
        composable(route = "movieList"){
            MovieListScreen(navControler)
        }
        composable(
            route = "movieDetail" + "/{itemId}",
            arguments = listOf(navArgument("itemId"){
                type = NavType.StringType
            })
        ){backStrackEntry ->
            val movieId = requireNotNull(backStrackEntry.arguments?.getString("itemId"))
            MovieDetailScreen(movieId, navControler)
        }
    }
}