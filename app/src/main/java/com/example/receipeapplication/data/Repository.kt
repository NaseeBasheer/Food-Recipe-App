package com.example.receipeapplication.data
import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Inject

//injects local data source into this repo

@ViewModelScoped
class Repository @Inject constructor(
    remoteDataSource: RemoteDataSource,
    localDataSource: LocalDataSource
) {
    val remote = remoteDataSource
    val local = localDataSource
}