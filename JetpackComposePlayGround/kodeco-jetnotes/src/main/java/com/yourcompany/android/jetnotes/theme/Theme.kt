package com.yourcompany.android.jetnotes.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

private val LightThemeColors = lightColors(
  primary = green,
  primaryVariant = greenDark,
  secondary = green
)

private val DarkThemeColors = lightColors(
  primary = green,
  primaryVariant = greenDark,
  secondary = green
)

/**
 * Responsible for switching color palette for dark and light theme.
 */
@Composable
fun JetNotesTheme(content: @Composable () -> Unit) {
  val isDarkThemeEnabled = isSystemInDarkTheme() || JetNotesThemeSettings.isDarkThemeEnabled
  val colors = if (isDarkThemeEnabled) DarkThemeColors else LightThemeColors

  MaterialTheme(colors = colors, content = content)
}

/**
 * Allows changing between light and a dark theme from the app's settings.
 */
object JetNotesThemeSettings {
  var isDarkThemeEnabled by mutableStateOf(false)
}
