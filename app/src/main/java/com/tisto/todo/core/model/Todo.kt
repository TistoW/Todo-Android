package com.tisto.todo.core.model

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Created by Tisto on 1/20/2021.
 */

data class Todo(
    val id:Int = 0,
    val name: String = "",
    val deskripsi: String? = null,
)