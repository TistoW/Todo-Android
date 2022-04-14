package com.tisto.todo.core.data.repo

import com.inyongtisto.myhelper.extension.getErrorBody
import com.tisto.todo.util.Constant
import com.tisto.todo.core.data.source.local.LocalDataSource
import com.tisto.todo.core.data.source.local.entity.TodoEntity
import com.tisto.todo.core.data.source.remote.RemoteDataSource
import com.tisto.todo.core.data.source.remote.model.User
import com.tisto.todo.core.data.source.remote.network.ResponseHandler
import com.tisto.todo.core.data.source.remote.request.LoginRequest
import com.tisto.todo.core.data.source.remote.response.base.DataResponse
import com.tisto.todo.core.data.source.remote.network.Resource
import com.tisto.todo.core.data.source.remote.request.RegisterRequest
import com.tisto.todo.core.data.source.remote.request.TodoRequest
import com.tisto.todo.core.data.source.remote.response.base.ListResponse
import com.tisto.todo.core.model.Todo
import com.tisto.todo.util.Prefs
import com.tisto.todo.util.defultError
import com.tisto.todo.util.setUser
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.Response

class AppRepository(val local: LocalDataSource, val remote: RemoteDataSource) {

    // Alternative for full control
    fun login(data: LoginRequest) = flow {
        emit(Resource.loading())
        try {
            remote.login(data).let {
                if (it.isSuccessful) {
                    val body = it.body()?.data

                    Prefs.isLogin = true
                    Prefs.token = body?.token
                    setUser(body)

                    emit(Resource.success(body))
                } else {
                    val errorMessage = it.getErrorBody()?.message.defultError()
                    emit(Resource.error(errorMessage, null))
                }
            }
        } catch (e: Exception) {
            emit(Resource.error(e.toString(), null))
        }
    }.flowOn(Dispatchers.IO)

    fun register(data: RegisterRequest) = object : ResponseHandler<User?, DataResponse<User>>() {
        override suspend fun createCall(): Response<DataResponse<User>> {
            return remote.register(data)
        }

        override suspend fun resultCall(data: DataResponse<User>): User? {
            return data.data
        }
    }.asFlow().flowOn(Dispatchers.IO)


    fun getTodo() = object : ResponseHandler<List<Todo>, ListResponse<Todo>>() {
        override suspend fun createCall(): Response<ListResponse<Todo>> {
            return remote.getTodo()
        }

        override suspend fun resultCall(data: ListResponse<Todo>): List<Todo> {
            return data.data
        }
    }.asFlow().flowOn(Dispatchers.IO)

    fun deleteTodo(mData: Todo) = object : ResponseHandler<Todo, DataResponse<Todo>>() {
        override suspend fun createCall(): Response<DataResponse<Todo>> {
            return remote.deleteTodo(mData)
        }

        override suspend fun resultCall(data: DataResponse<Todo>): Todo {
            return data.data ?: Todo()
        }
    }.asFlow().flowOn(Dispatchers.IO)

    fun createTodo(data: TodoRequest) = object : ResponseHandler<Todo, DataResponse<Todo>>() {
        override suspend fun createCall(): Response<DataResponse<Todo>> {
            return remote.createTodo(data)
        }

        override suspend fun resultCall(data: DataResponse<Todo>): Todo {
            return data.data ?: Todo()
        }
    }.asFlow().flowOn(Dispatchers.IO)

    fun updateTodo(data: Todo) = object : ResponseHandler<Todo, DataResponse<Todo>>() {
        override suspend fun createCall(): Response<DataResponse<Todo>> {
            return remote.updateTodo(data)
        }

        override suspend fun resultCall(data: DataResponse<Todo>): Todo {
            return data.data ?: Todo()
        }
    }.asFlow().flowOn(Dispatchers.IO)


    /**************************** Local **************************/
    fun getAllTodo() = local.getAllTodo()
    fun searchTodo(search: String) = local.searchTodo(search)
    fun insertTodo(body: TodoEntity) = local.insertTodo(body)
    fun updateTodo(body: TodoEntity) = local.updateTodo(body)
    fun clearTodo() = local.clearTodo()
    fun deleteTodo(data: TodoEntity) = local.deleteTodo(data)
}