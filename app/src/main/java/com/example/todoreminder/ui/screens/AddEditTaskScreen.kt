package com.example.todoreminder.ui.screens

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.todoreminder.data.TaskEntity
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun AddEditTaskScreen(title: String, initial: TaskEntity?, onSave: (TaskEntity) -> Unit, onCancel: () -> Unit) {
    val ctx = LocalContext.current
    var t by remember { mutableStateOf(initial?.title ?: "") }
    var d by remember { mutableStateOf(initial?.description ?: "") }
    var dueAt by remember { mutableStateOf(initial?.dueAtMillis) }
    var remindAt by remember { mutableStateOf(initial?.remindAtMillis) }

    val fmt = SimpleDateFormat("dd.MM.yyyy HH:mm", Locale.getDefault())

    fun pickDateTime(current: Long?, onPicked: (Long) -> Unit) {
        val cal = Calendar.getInstance().apply { if (current != null) timeInMillis = current }
        DatePickerDialog(ctx, { _, y, m, day ->
            cal.set(Calendar.YEAR, y); cal.set(Calendar.MONTH, m); cal.set(Calendar.DAY_OF_MONTH, day)
            TimePickerDialog(ctx, { _, hh, mm ->
                cal.set(Calendar.HOUR_OF_DAY, hh); cal.set(Calendar.MINUTE, mm); cal.set(Calendar.SECOND, 0)
                onPicked(cal.timeInMillis)
            }, cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE), true).show()
        }, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH)).show()
    }

    Scaffold(topBar = { TopAppBar(title = { Text(title) }) }) { padding ->
        Column(
            Modifier.fillMaxSize().padding(padding).padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            OutlinedTextField(value = t, onValueChange = { t = it }, label = { Text("Tytuł") }, modifier = Modifier.fillMaxWidth())
            OutlinedTextField(value = d, onValueChange = { d = it }, label = { Text("Opis (opcjonalnie)") }, modifier = Modifier.fillMaxWidth())

            Card(Modifier.fillMaxWidth()) {
                Column(Modifier.padding(12.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text("Daty", style = MaterialTheme.typography.titleMedium)

                    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                        Text(dueAt?.let { "Termin: ${fmt.format(Date(it))}" } ?: "Termin: brak")
                        Row {
                            TextButton(onClick = { pickDateTime(dueAt) { dueAt = it } }) { Text("Ustaw") }
                            TextButton(onClick = { dueAt = null }) { Text("Wyczyść") }
                        }
                    }

                    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                        Text(remindAt?.let { "Przypomnienie: ${fmt.format(Date(it))}" } ?: "Przypomnienie: brak")
                        Row {
                            TextButton(onClick = { pickDateTime(remindAt) { remindAt = it } }) { Text("Ustaw") }
                            TextButton(onClick = { remindAt = null }) { Text("Wyczyść") }
                        }
                    }
                }
            }

            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                OutlinedButton(modifier = Modifier.weight(1f), onClick = onCancel) { Text("Anuluj") }
                Button(
                    modifier = Modifier.weight(1f),
                    onClick = {
                        if (t.trim().isBlank()) return@Button
                        val base = initial ?: TaskEntity(title = t.trim())
                        onSave(base.copy(title = t.trim(), description = d.trim(), dueAtMillis = dueAt, remindAtMillis = remindAt))
                    }
                ) { Text("Zapisz") }
            }
        }
    }
}
