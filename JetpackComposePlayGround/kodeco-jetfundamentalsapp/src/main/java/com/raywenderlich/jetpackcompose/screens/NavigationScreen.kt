package com.raywenderlich.jetpackcompose.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.raywenderlich.jetpackcompose.R
import com.raywenderlich.jetpackcompose.router.JetFundamentalsRouter
import com.raywenderlich.jetpackcompose.router.Screen

@Composable
fun NavigationScreen() {
  Surface(
      color = Color.White,
      modifier = Modifier.fillMaxSize()
  ) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

      NavigationButton(stringResource(id = R.string.text), Screen.Text)
      NavigationButton(stringResource(id = R.string.text_field), Screen.TextField)
      NavigationButton(stringResource(id = R.string.buttons), Screen.Buttons)
      NavigationButton(stringResource(id = R.string.progress_indicators), Screen.ProgressIndicator)
      NavigationButton(stringResource(id = R.string.alert_dialog), Screen.AlertDialog)
      NavigationButton(stringResource(id = R.string.row), Screen.Row)
      NavigationButton(stringResource(id = R.string.column), Screen.Column)
      NavigationButton(stringResource(id = R.string.box), Screen.Box)
      NavigationButton(stringResource(id = R.string.surface), Screen.Surface)
      NavigationButton(stringResource(id = R.string.scaffold), Screen.Scaffold)
    }
  }
}

@Composable
fun NavigationButton(text: String, screen: Screen) {
  Button(
      modifier = Modifier
          .fillMaxWidth()
          .padding(start = 16.dp, end = 16.dp, top = 16.dp),
      shape = RoundedCornerShape(4.dp),
      colors = ButtonDefaults.buttonColors(backgroundColor = colorResource(id = R.color.colorPrimary)),
      onClick = { JetFundamentalsRouter.navigateTo(screen) }
  ) {
    Text(
        text = text,
        color = Color.White
    )
  }
}