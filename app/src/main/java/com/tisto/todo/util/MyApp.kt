package com.tisto.todo.util

import android.app.Application
import com.chibatching.kotpref.Kotpref
import com.tisto.todo.core.di.repositoryModule
import com.tisto.todo.core.di.viewModelModule
import com.tisto.todo.core.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MyApp : Application() {
    override fun onCreate() {
        super.onCreate()
        Kotpref.init(this)
        startKoin {
            androidContext(this@MyApp)
            modules(listOf(appModule, viewModelModule, repositoryModule))
        }
    }
}