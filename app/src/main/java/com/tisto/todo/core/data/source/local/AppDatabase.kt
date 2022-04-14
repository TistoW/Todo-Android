package com.tisto.todo.core.data.source.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.tisto.todo.core.data.source.local.dao.TodoDao
import com.tisto.todo.core.data.source.local.entity.TodoEntity
import com.tisto.todo.util.Constant

@Database(
        entities = [
            TodoEntity::class,
        ], version = Constant.DB_VERSION, exportSchema = false
)

abstract class AppDatabase : RoomDatabase() {
    abstract fun todoDao(): TodoDao
}