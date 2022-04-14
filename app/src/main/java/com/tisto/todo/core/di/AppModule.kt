package com.tisto.todo.core.di

import androidx.room.Room
import com.tisto.todo.core.data.source.local.AppDatabase
import com.tisto.todo.core.data.source.local.LocalDataSource
import com.tisto.todo.core.data.source.remote.RemoteDataSource
import com.tisto.todo.core.data.source.remote.network.ApiConfig
import com.tisto.todo.util.Constant
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val appModule = module {

    single {
        Room.databaseBuilder(
                androidContext(),
                AppDatabase::class.java,
                Constant.DB_NAME
        ).build()
    }

    single { ApiConfig.provideApiService }

    single { RemoteDataSource(get()) }

    single { LocalDataSource(get()) }
}