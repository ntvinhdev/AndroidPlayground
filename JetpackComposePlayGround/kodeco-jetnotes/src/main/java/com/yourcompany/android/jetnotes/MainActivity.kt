package com.yourcompany.android.jetnotes

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.activity.compose.setContent
import androidx.compose.compiler.plugins.kotlin.EmptyFunctionMetrics.composable
import androidx.compose.material.Scaffold
import androidx.compose.material.ScaffoldState
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.yourcompany.android.jetnotes.routing.Screen
import com.yourcompany.android.jetnotes.screens.NotesScreen
import com.yourcompany.android.jetnotes.theme.JetNotesTheme
import com.yourcompany.android.jetnotes.ui.components.AppDrawer
import com.yourcompany.android.jetnotes.ui.components.Note
import com.yourcompany.android.jetnotes.viewmodel.MainViewModel
import com.yourcompany.android.jetnotes.viewmodel.MainViewModelFactory
import kotlinx.coroutines.launch

/**
 * Main activity for the app.
 */
class MainActivity : AppCompatActivity() {

  private val viewModel: MainViewModel by viewModels(factoryProducer = {
    MainViewModelFactory(
      this,
      (application as JetNotesApplication).dependencyInjector.repository
    )
  })

  @SuppressLint("UnusedMaterialScaffoldPaddingParameter")
  override fun onCreate(savedInstanceState: Bundle?) {
    // Switch to AppTheme for displaying the activity
    setTheme(R.style.Theme_JetNotes)

    super.onCreate(savedInstanceState)

    setContent {
      JetNotesTheme {
        val coroutineScope = rememberCoroutineScope()
        val scaffoldState: ScaffoldState = rememberScaffoldState()
        val navController: NavHostController = rememberNavController()
        Scaffold(
          scaffoldState = scaffoldState,
          drawerContent = {
            AppDrawer(
              currentScreen = Screen.Notes,
              onScreenSelected = { screen ->
                coroutineScope.launch {
                  scaffoldState.drawerState.close()
                }
              }
            )
          }
        ) {
          NavHost(
            navController = navController,
            startDestination = Screen.Notes.route,
            builder = {
              composable(Screen.Notes.route) {
                NotesScreen(viewModel)
              }
            }
          )
        }
      }
    }
  }
}
