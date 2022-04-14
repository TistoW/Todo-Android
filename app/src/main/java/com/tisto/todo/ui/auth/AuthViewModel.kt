package com.tisto.todo.ui.auth

import androidx.lifecycle.*
import com.tisto.todo.core.data.repo.AppRepository
import com.tisto.todo.core.data.source.remote.request.LoginRequest
import com.tisto.todo.core.data.source.remote.request.RegisterRequest
import kotlinx.coroutines.launch

class AuthViewModel(private val repository: AppRepository) : ViewModel() {

    fun login(data: LoginRequest) = repository.login(data).asLiveData()

    fun register(data: RegisterRequest) = repository.register(data).asLiveData()

}