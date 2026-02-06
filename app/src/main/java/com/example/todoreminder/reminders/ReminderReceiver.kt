package com.example.todoreminder.reminders

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class ReminderReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val taskId = intent.getLongExtra("task_id", -1L)
        val title = intent.getStringExtra("title") ?: "Zadanie"
        val desc = intent.getStringExtra("desc") ?: ""
        NotificationHelper.ensureChannel(context)
        if (taskId != -1L) NotificationHelper.showReminder(context, taskId, title, desc)
    }
}
