package com.tisto.todo.core.data.source.remote

import com.tisto.todo.core.data.source.remote.network.ApiService
import com.tisto.todo.core.data.source.remote.request.LoginRequest
import com.tisto.todo.core.data.source.remote.request.RegisterRequest
import com.tisto.todo.core.data.source.remote.request.TodoRequest
import com.tisto.todo.core.model.Todo

class RemoteDataSource(private val api: ApiService) {

    suspend fun login(data: LoginRequest) = api.login(data)

    suspend fun register(data: RegisterRequest) = api.register(data)

    suspend fun getTodo(page: Int = 1, search: String? = null) = api.getTodo(page, search)

    suspend fun getTodo() = api.getTodo()
    suspend fun deleteTodo(mData: Todo) = api.deleteTodo(mData.id)
    suspend fun createTodo(data: TodoRequest) = api.createTodo(data)
    suspend fun updateTodo(data: Todo) = api.updateTodo(data.id, data)

}