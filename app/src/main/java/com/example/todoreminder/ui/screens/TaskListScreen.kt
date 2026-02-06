package com.example.todoreminder.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.todoreminder.data.TaskEntity
import com.example.todoreminder.vm.TaskViewModel
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun TaskListScreen(vm: TaskViewModel, onAdd: () -> Unit, onEdit: (Long) -> Unit, onSettings: () -> Unit) {
    val tasks = vm.tasks.value
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("To-Do z przypomnieniami") },
                actions = { TextButton(onClick = onSettings) { Text("Ustawienia") } }
            )
        },
        floatingActionButton = { FloatingActionButton(onClick = onAdd) { Text("+") } }
    ) { padding ->
        if (tasks.isEmpty()) {
            Box(Modifier.fillMaxSize().padding(padding), contentAlignment = Alignment.Center) {
                Text("Brak zadań. Dodaj pierwsze +")
            }
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize().padding(padding),
                contentPadding = PaddingValues(12.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(tasks, key = { it.id }) { task ->
                    TaskRow(
                        task = task,
                        onToggle = { vm.toggleDone(task) },
                        onDelete = { vm.deleteTask(task) },
                        onClick = { onEdit(task.id) }
                    )
                }
            }
        }
    }
}

@Composable
private fun TaskRow(task: TaskEntity, onToggle: () -> Unit, onDelete: () -> Unit, onClick: () -> Unit) {
    val fmt = SimpleDateFormat("dd.MM.yyyy HH:mm", Locale.getDefault())
    val due = task.dueAtMillis?.let { "Termin: ${fmt.format(Date(it))}" } ?: ""
    val rem = task.remindAtMillis?.let { "Przyp.: ${fmt.format(Date(it))}" } ?: ""

    Card(Modifier.fillMaxWidth().clickable { onClick() }) {
        Column(Modifier.padding(12.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Checkbox(checked = task.isDone, onCheckedChange = { onToggle() })
                Spacer(Modifier.width(8.dp))
                Column(Modifier.weight(1f)) {
                    Text(task.title, style = MaterialTheme.typography.titleMedium)
                    if (task.description.isNotBlank()) Text(task.description, style = MaterialTheme.typography.bodyMedium)
                }
                TextButton(onClick = onDelete) { Text("Usuń") }
            }
            if (due.isNotBlank() || rem.isNotBlank()) {
                Spacer(Modifier.height(6.dp))
                if (due.isNotBlank()) Text(due, style = MaterialTheme.typography.bodySmall)
                if (rem.isNotBlank()) Text(rem, style = MaterialTheme.typography.bodySmall)
            }
        }
    }
}
