package com.example.todoreminder.vm

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.todoreminder.repo.TaskRepository

class TaskViewModelFactory(private val app: Application, private val repo: TaskRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return TaskViewModel(app, repo) as T
    }
}
