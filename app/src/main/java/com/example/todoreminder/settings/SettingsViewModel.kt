package com.example.todoreminder.settings

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todoreminder.TodoApp
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class SettingsViewModel(private val app: TodoApp) : ViewModel() {
    val darkThemeState = mutableStateOf(false)

    init {
        viewModelScope.launch {
            SettingsDataStore.darkThemeFlow(app).collectLatest { darkThemeState.value = it }
        }
    }

    fun setDarkTheme(v: Boolean) {
        viewModelScope.launch { SettingsDataStore.setDarkTheme(app, v) }
    }
}
