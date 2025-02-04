package com.devspacecinenow

import android.app.Application
import androidx.room.Room
import com.devspacecinenow.List.data.MovieListRepository
import com.devspacecinenow.List.data.local.MovieListLocalDataSource
import com.devspacecinenow.List.data.remote.ListService
import com.devspacecinenow.List.data.remote.MovieListRemoteDataSource
import com.devspacecinenow.common.data.remote.RetrofitClient
import com.devspacecinenow.common.data.local.CineNowDataBase

class CineNowApplication : Application (){

    private  val db by lazy {
        Room.databaseBuilder(
            applicationContext,
            CineNowDataBase::class.java, "database-cine-now"
        ).build()
    }

   private val listService by lazy {
        RetrofitClient.retrofitInstance.create(ListService::class.java)

    }

    private val localDataSource: MovieListLocalDataSource by lazy {
        MovieListLocalDataSource(db.getMovieDao())
    }

    private val remoteDataSource: MovieListRemoteDataSource by lazy {
        MovieListRemoteDataSource(listService)
    }

    val repository: MovieListRepository by lazy {
        MovieListRepository(
            local = localDataSource,
            remote = remoteDataSource
        )
    }
}