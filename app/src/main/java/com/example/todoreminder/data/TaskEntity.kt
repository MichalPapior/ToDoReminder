package com.example.todoreminder.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tasks")
data class TaskEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val title: String,
    val description: String = "",
    val dueAtMillis: Long? = null,
    val remindAtMillis: Long? = null,
    val isDone: Boolean = false,
    val createdAtMillis: Long = System.currentTimeMillis()
)
