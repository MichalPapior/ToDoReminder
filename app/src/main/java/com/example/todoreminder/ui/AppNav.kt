package com.example.todoreminder.ui

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.todoreminder.TodoApp
import com.example.todoreminder.settings.SettingsViewModel
import com.example.todoreminder.ui.screens.AddEditTaskScreen
import com.example.todoreminder.ui.screens.SettingsScreen
import com.example.todoreminder.ui.screens.TaskListScreen
import com.example.todoreminder.vm.TaskViewModel
import com.example.todoreminder.vm.TaskViewModelFactory

object Routes {
    const val LIST = "list"
    const val ADD = "add"
    const val EDIT = "edit/{id}"
    const val SETTINGS = "settings"
}

@Composable
fun AppNav(app: TodoApp, settingsVm: SettingsViewModel) {
    val nav = rememberNavController()
    val taskVm: TaskViewModel = viewModel(factory = TaskViewModelFactory(app, app.repo))

    NavHost(navController = nav, startDestination = Routes.LIST) {
        composable(Routes.LIST) {
            TaskListScreen(
                vm = taskVm,
                onAdd = { nav.navigate(Routes.ADD) },
                onEdit = { id -> nav.navigate("edit/$id") },
                onSettings = { nav.navigate(Routes.SETTINGS) }
            )
        }
        composable(Routes.ADD) {
            AddEditTaskScreen(
                title = "Dodaj zadanie",
                initial = null,
                onSave = { t ->
                    taskVm.addTask(t.title, t.description, t.dueAtMillis, t.remindAtMillis)
                    nav.popBackStack()
                },
                onCancel = { nav.popBackStack() }
            )
        }
        composable(Routes.EDIT, arguments = listOf(navArgument("id") { type = NavType.LongType })) { backStack ->
            val id = backStack.arguments?.getLong("id") ?: 0L
            AddEditTaskScreen(
                title = "Edytuj zadanie",
                initial = taskVm.tasks.value.firstOrNull { it.id == id },
                onSave = { t -> taskVm.updateTask(t); nav.popBackStack() },
                onCancel = { nav.popBackStack() }
            )
        }
        composable(Routes.SETTINGS) {
            SettingsScreen(
                darkTheme = settingsVm.darkThemeState.value,
                onDarkThemeChange = settingsVm::setDarkTheme,
                onBack = { nav.popBackStack() }
            )
        }
    }
}
