package com.ntvinhdev.kodeco.jetreddit.routing

import androidx.activity.OnBackPressedDispatcher
import androidx.compose.runtime.*

private val localBackPressedDispatcher = staticCompositionLocalOf<OnBackPressedDispatcher?> { null }

@Composable
fun BackButtonHandler(
    enabled: Boolean = true,
    onBackPressed: () -> Unit
) {
  //TODO Add your code here
}

@Composable
fun BackButtonAction(onBackPressed: () -> Unit) {
  //TODO Add your code here
}
