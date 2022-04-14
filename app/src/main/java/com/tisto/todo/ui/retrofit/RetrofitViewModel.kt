package com.tisto.todo.ui.retrofit

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.tisto.todo.core.data.repo.AppRepository
import com.tisto.todo.core.data.source.remote.request.TodoRequest
import com.tisto.todo.core.model.Todo

class RetrofitViewModel(private val repository: AppRepository) : ViewModel() {

    fun getAll() = repository.getTodo().asLiveData()

    fun delete(data: Todo) = repository.deleteTodo(data).asLiveData()

    fun create(data: TodoRequest) = repository.createTodo(data).asLiveData()

    fun update(data: Todo) = repository.updateTodo(data).asLiveData()

}