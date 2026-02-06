package com.example.todoreminder.repo

import com.example.todoreminder.data.TaskDao
import com.example.todoreminder.data.TaskEntity
import kotlinx.coroutines.flow.Flow

class TaskRepository(private val dao: TaskDao) {
    fun observeAll(): Flow<List<TaskEntity>> = dao.observeAll()
    suspend fun insert(task: TaskEntity): Long = dao.insert(task)
    suspend fun update(task: TaskEntity) = dao.update(task)
    suspend fun delete(task: TaskEntity) = dao.delete(task)
}
