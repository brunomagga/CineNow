package com.devspacecinenow.List.presentation.ui

import com.google.gson.annotations.SerializedName

data class MovieListUIState(
    val list : List<MovieUIData> = emptyList(),
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val errorMessage : String = "something went wrong"
)



data class MovieUIData(
    val id: Int,
    val title: String,
    val overview: String,
    val image: String,
)
