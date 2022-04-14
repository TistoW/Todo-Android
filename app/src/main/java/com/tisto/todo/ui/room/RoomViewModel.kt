package com.tisto.todo.ui.room

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.tisto.todo.core.data.repo.AppRepository
import com.tisto.todo.core.data.source.local.entity.TodoEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RoomViewModel(private val repository: AppRepository) : ViewModel() {

    fun getAll() = repository.getAllTodo().asLiveData()

    fun search(search: String) = repository.searchTodo(search).asLiveData()

    fun delete(data: TodoEntity) = viewModelScope.launch(Dispatchers.IO) {
        repository.deleteTodo(data)
    }

    fun create(data: TodoEntity) = viewModelScope.launch(Dispatchers.IO) {
        repository.insertTodo(data)
    }

    fun update(data: TodoEntity) = viewModelScope.launch(Dispatchers.IO) {
        repository.updateTodo(data)
    }

}