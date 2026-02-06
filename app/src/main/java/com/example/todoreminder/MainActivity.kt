package com.example.todoreminder

import android.Manifest
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import com.example.todoreminder.settings.SettingsViewModel
import com.example.todoreminder.ui.AppNav

class MainActivity : ComponentActivity() {

    private val requestNotifPermission =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (Build.VERSION.SDK_INT >= 33) {
            requestNotifPermission.launch(Manifest.permission.POST_NOTIFICATIONS)
        }

        val app = application as TodoApp
        val settingsVm = SettingsViewModel(app)

        setContent {
            Surface(color = MaterialTheme.colorScheme.background) {
                AppNav(app = app, settingsVm = settingsVm)
            }
        }
    }
}
