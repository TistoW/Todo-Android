package com.tisto.todo.core.data.source.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Created by Tisto on 1/20/2021.
 */

@Entity
data class TodoEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String = "",
    val deskripsi: String? = null,
)