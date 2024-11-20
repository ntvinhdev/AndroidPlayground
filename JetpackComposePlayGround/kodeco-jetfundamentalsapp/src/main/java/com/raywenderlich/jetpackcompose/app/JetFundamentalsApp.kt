package com.raywenderlich.jetpackcompose.app

import androidx.compose.animation.Crossfade
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import com.raywenderlich.jetpackcompose.router.JetFundamentalsRouter
import com.raywenderlich.jetpackcompose.router.Screen
import com.raywenderlich.jetpackcompose.screens.AlertDialogScreen
import com.raywenderlich.jetpackcompose.screens.BoxScreen
import com.raywenderlich.jetpackcompose.screens.ColumnScreen
import com.yourcompany.android.jetpackcompose.screens.ExploreButtonsScreen
import com.raywenderlich.jetpackcompose.screens.NavigationScreen
import com.raywenderlich.jetpackcompose.screens.ProgressIndicatorScreen
import com.raywenderlich.jetpackcompose.screens.RowScreen
import com.raywenderlich.jetpackcompose.screens.ScaffoldScreen
import com.raywenderlich.jetpackcompose.screens.SurfaceScreen
import com.raywenderlich.jetpackcompose.screens.TextFieldScreen
import com.raywenderlich.jetpackcompose.screens.TextScreen

@Composable
fun JetFundamentalsApp() {
  Surface(color = MaterialTheme.colors.background) {
    Crossfade(targetState = JetFundamentalsRouter.currentScreen) { screenState ->
      when (screenState.value) {
        is Screen.Navigation -> NavigationScreen()
        is Screen.Text -> TextScreen()
        is Screen.TextField -> TextFieldScreen()
        is Screen.Buttons -> ExploreButtonsScreen()
        is Screen.ProgressIndicator -> ProgressIndicatorScreen()
        is Screen.AlertDialog -> AlertDialogScreen()
        is Screen.Row -> RowScreen()
        is Screen.Column -> ColumnScreen()
        is Screen.Box -> BoxScreen()
        is Screen.Surface -> SurfaceScreen()
        is Screen.Scaffold -> ScaffoldScreen()
      }
    }
  }
}