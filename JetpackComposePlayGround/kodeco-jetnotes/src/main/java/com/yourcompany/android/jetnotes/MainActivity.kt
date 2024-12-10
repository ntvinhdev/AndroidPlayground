package com.yourcompany.android.jetnotes

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.activity.compose.setContent
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Scaffold
import androidx.compose.material.ScaffoldState
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.yourcompany.android.jetnotes.routing.Screen
import com.yourcompany.android.jetnotes.screens.NotesScreen
import com.yourcompany.android.jetnotes.theme.JetNotesTheme
import com.yourcompany.android.jetnotes.ui.components.AppDrawer
import com.yourcompany.android.jetnotes.ui.screens.SaveNoteScreen
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

        // navBackStackEntry by navController.currentBackStackEntryAsState()
        val navBackStackEntry by navController.currentBackStackEntryAsState()

        Scaffold(
          scaffoldState = scaffoldState,
          drawerContent = {
            AppDrawer(
              currentScreen = Screen.fromRoute(
                route = navBackStackEntry?.destination?.route
              ),
              onScreenSelected = { screen ->
                navController.navigate(screen.route) {
                  popUpTo(
                    id = navController.graph.findStartDestination().id
                  ) {
                    saveState = true
                  }

                  launchSingleTop = true
                  restoreState = true
                }
                coroutineScope.launch {
                  scaffoldState.drawerState.close()
                }
              }
            )
          },
          content = {
            MainActivityScreen(
              navController = navController,
              viewModel = viewModel,
              openNavigationDrawer = {
                coroutineScope.launch {
                  scaffoldState.drawerState.open()
                }
              }
            )
          }
        )
      }
    }
  }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun MainActivityScreen(
  navController: NavHostController,
  viewModel: MainViewModel,
  openNavigationDrawer: () -> Unit
) {
  NavHost(
    navController = navController,
    startDestination = Screen.Notes.route
  ) {
    composable(route = Screen.Notes.route) {
      NotesScreen(
        viewModel,
        openNavigationDrawer,
        onNavigateToSaveNote = { navController.navigate(Screen.SaveNote.route) }
      )
    }
    composable(route = Screen.SaveNote.route) {
      SaveNoteScreen(
        viewModel,
        { navController.popBackStack() }
      )
    }
    composable(route = Screen.Trash.route) {
      TrashScreen(viewModel, openNavigationDrawer)
    }
  }
}

@Composable
fun TrashScreen(viewModel: MainViewModel, openNavigationDrawer: () -> Unit) {
  TODO("Not yet implemented")
}
