package com.yourcompany.android.jetpackcompose.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.RadioButton
import androidx.compose.material.RadioButtonDefaults
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.raywenderlich.jetpackcompose.R
import com.raywenderlich.jetpackcompose.router.BackButtonHandler
import com.raywenderlich.jetpackcompose.router.JetFundamentalsRouter
import com.raywenderlich.jetpackcompose.router.Screen

@Composable
fun ExploreButtonsScreen() {
  Column(
    modifier = Modifier.fillMaxSize(),
    horizontalAlignment = Alignment.CenterHorizontally,
    verticalArrangement = Arrangement.Center
  ) {

    MyButton()
    MyRadioGroup()
    MyFloatingActionButton()

    BackButtonHandler {
      JetFundamentalsRouter.navigateTo(Screen.Navigation)
    }
  }
}

@Composable
fun MyButton() {
  Button(
    onClick = {},
    colors = ButtonDefaults.buttonColors(backgroundColor = colorResource(id = R.color.colorPrimary)),
    border = BorderStroke(
      1.dp,
      color = colorResource(id = R.color.colorPrimaryDark)
    )
  ) {
    Text(
      text = stringResource(id = R.string.button_text),
      color = Color.White
    )
  }
}

@Composable
fun MyRadioGroup() {
  val radioButtons = listOf(0, 1, 2) // 1
  val selectedButton = remember { mutableStateOf(radioButtons.first()) } // 2
  Column {
    radioButtons.forEach { index ->
      val isSelected = index == selectedButton.value
      val colors = RadioButtonDefaults.colors(
        selectedColor = colorResource(id = R.color.colorPrimary),
        unselectedColor = colorResource(id = R.color.colorPrimaryDark),
        disabledColor = Color.LightGray
      )
      RadioButton(
        colors = colors,
        selected = isSelected,
        onClick = { selectedButton.value = index }
      )
    }
  }
}

@Composable
fun MyFloatingActionButton() {
  FloatingActionButton(
    onClick = {},
    backgroundColor = colorResource(id = R.color.colorPrimary),
    contentColor = Color.White,
    content = {
      Icon(Icons.Filled.Favorite, contentDescription = "Test FAB")
    }
  )
}
