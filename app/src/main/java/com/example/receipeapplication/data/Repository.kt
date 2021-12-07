package com.example.receipeapplication.data

import dagger.hilt.android.scopes.ActivityRetainedScoped
import javax.inject.Inject

//injects local data source into this repo

@ActivityRetainedScoped
class Repository @Inject constructor(
    remoteDataSource: RemoteDataSource,
    localDataSource: LocalDataSource
) {
    val remote = remoteDataSource
    val local = localDataSource
}