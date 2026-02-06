package com.example.todoreminder.settings

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore by preferencesDataStore(name = "settings")

object SettingsDataStore {
    private val DARK = booleanPreferencesKey("dark_theme")
    fun darkThemeFlow(context: Context): Flow<Boolean> =
        context.dataStore.data.map { it[DARK] ?: false }
    suspend fun setDarkTheme(context: Context, value: Boolean) {
        context.dataStore.edit { it[DARK] = value }
    }
}
