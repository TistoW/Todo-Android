package com.tisto.todo.core.data.source.local

import com.tisto.todo.core.data.source.local.entity.TodoEntity
import com.tisto.todo.util.searchQuery

class LocalDataSource(private val db: AppDatabase) {

    fun getAllTodo() = db.todoDao().getAll()
    fun searchTodo(search: String) = db.todoDao().search(search.searchQuery())
    fun insertTodo(body: TodoEntity) = db.todoDao().insert(body)
    fun updateTodo(body: TodoEntity) = db.todoDao().update(body)
    fun clearTodo() = db.todoDao().clear()
    fun deleteTodo(data: TodoEntity) = db.todoDao().delete(data)

}