package com.tisto.todo.core.di

import com.tisto.todo.ui.auth.AuthViewModel
import com.tisto.todo.ui.retrofit.RetrofitViewModel
import com.tisto.todo.ui.room.RoomViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { AuthViewModel(get()) }
    viewModel { RetrofitViewModel(get()) }
    viewModel { RoomViewModel(get()) }
}