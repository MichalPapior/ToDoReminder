package com.example.todoreminder.vm

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todoreminder.data.TaskEntity
import com.example.todoreminder.reminders.AlarmScheduler
import com.example.todoreminder.repo.TaskRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class TaskViewModel(private val app: Application, private val repo: TaskRepository) : ViewModel() {
    val tasks = repo.observeAll()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), emptyList())

    fun addTask(title: String, description: String, dueAtMillis: Long?, remindAtMillis: Long?) {
        viewModelScope.launch {
            val id = repo.insert(TaskEntity(title = title.trim(), description = description.trim(), dueAtMillis = dueAtMillis, remindAtMillis = remindAtMillis))
            scheduleIfNeeded(id, title, description, remindAtMillis, isDone = false)
        }
    }

    fun updateTask(task: TaskEntity) {
        viewModelScope.launch {
            repo.update(task)
            scheduleIfNeeded(task.id, task.title, task.description, task.remindAtMillis, task.isDone)
        }
    }

    fun deleteTask(task: TaskEntity) {
        viewModelScope.launch {
            repo.delete(task)
            AlarmScheduler.cancel(app, task.id)
        }
    }

    fun toggleDone(task: TaskEntity) = updateTask(task.copy(isDone = !task.isDone))

    private fun scheduleIfNeeded(id: Long, title: String, desc: String, remindAtMillis: Long?, isDone: Boolean) {
        if (isDone) { AlarmScheduler.cancel(app, id); return }
        val t = remindAtMillis ?: return
        if (t <= System.currentTimeMillis()) { AlarmScheduler.cancel(app, id); return }
        AlarmScheduler.schedule(app, id, t, title, desc)
    }
}
