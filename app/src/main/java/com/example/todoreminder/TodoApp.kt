package com.example.todoreminder

import android.app.Application
import com.example.todoreminder.data.AppDatabase
import com.example.todoreminder.repo.TaskRepository
import com.example.todoreminder.reminders.NotificationHelper

class TodoApp : Application() {
    lateinit var db: AppDatabase
        private set
    lateinit var repo: TaskRepository
        private set

    override fun onCreate() {
        super.onCreate()
        NotificationHelper.ensureChannel(this)
        db = AppDatabase.build(this)
        repo = TaskRepository(db.taskDao())
    }
}
