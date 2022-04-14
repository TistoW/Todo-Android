package com.tisto.todo.core.di

import com.tisto.todo.core.data.repo.AppRepository
import org.koin.dsl.module

val repositoryModule = module {
    single { AppRepository(get(), get()) }
}