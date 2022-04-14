package com.tisto.todo.core.data.source.local.dao

import androidx.room.*
import androidx.room.OnConflictStrategy.REPLACE
import com.tisto.todo.core.data.source.local.entity.TodoEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TodoDao {

    @Insert(onConflict = REPLACE)
    fun insert(data: TodoEntity)

    @Insert(onConflict = REPLACE)
    fun insert(data: List<TodoEntity>)

    @Update
    fun update(data: TodoEntity)

    @Update
    fun update(data: List<TodoEntity>)

    @Delete
    fun delete(data: TodoEntity)

    @Query("SELECT * from TodoEntity ORDER BY id DESC")
    fun getAll(): Flow<List<TodoEntity>>

    @Query("SELECT * from TodoEntity WHERE id = :id LIMIT 1")
    fun getOne(id: Int): Flow<TodoEntity?>

    @Query("SELECT * from TodoEntity WHERE name LIKE :queryString")
    fun search(queryString: String): Flow<List<TodoEntity>>

    @Query("DELETE FROM TodoEntity")
    fun clear()
}