package com.ilhmdhn.simranda.di

import android.content.Context
import com.ilhmdhn.simranda.data.DataRepository
import com.ilhmdhn.simranda.data.source.local.LocalDataSource
import com.ilhmdhn.simranda.data.source.local.room.ShowDatabase
import com.ilhmdhn.simranda.data.source.remote.RemoteDataSource
import com.ilhmdhn.simranda.utils.AppExecutors

object Injection {
    fun provideRepository(context: Context): DataRepository {

        val database = ShowDatabase.getInstance(context)

        val remoteDataSource = RemoteDataSource.getInstance()
        val localDataSource = LocalDataSource.getInstance(database.simrandaDao())
        val appExecutors = AppExecutors()

        return DataRepository.getInstance(remoteDataSource, localDataSource, appExecutors)
    }
}