package com.tisto.todo.core.data.source.remote.network

import com.tisto.todo.core.data.source.remote.model.User
import com.tisto.todo.core.data.source.remote.request.LoginRequest
import com.tisto.todo.core.data.source.remote.request.RegisterRequest
import com.tisto.todo.core.data.source.remote.request.TodoRequest
import com.tisto.todo.core.data.source.remote.response.base.DataResponse
import com.tisto.todo.core.data.source.remote.response.base.ListResponse
import com.tisto.todo.core.model.Todo
import retrofit2.Response
import retrofit2.http.*

interface ApiService {

    @POST("login")
    suspend fun login(
        @Body login: LoginRequest,
    ): Response<DataResponse<User>>

    @POST("register")
    suspend fun register(
        @Body login: RegisterRequest,
    ): Response<DataResponse<User>>

    @GET("todo")
    suspend fun getTodo(
        @Query("page") page: Int = 1,
        @Query("search") search: String? = null
    ): Response<ListResponse<Todo>>

    @GET("todo")
    suspend fun getTodo(): Response<ListResponse<Todo>>

    @POST("todo")
    suspend fun createTodo(
        @Body bankUser: TodoRequest
    ): Response<DataResponse<Todo>>

    @PUT("todo/{id}")
    suspend fun updateTodo(
        @Path("id") id: Int? = null,
        @Body bankUser: Todo
    ): Response<DataResponse<Todo>>

    @DELETE("todo/{id}")
    suspend fun deleteTodo(
        @Path("id") id: Int? = null
    ): Response<DataResponse<Todo>>

}