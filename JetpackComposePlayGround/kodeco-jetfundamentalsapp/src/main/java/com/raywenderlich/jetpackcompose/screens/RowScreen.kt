package com.raywenderlich.jetpackcompose.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.sp
import com.raywenderlich.jetpackcompose.R
import com.raywenderlich.jetpackcompose.router.BackButtonHandler
import com.raywenderlich.jetpackcompose.router.JetFundamentalsRouter
import com.raywenderlich.jetpackcompose.router.Screen

val THREE_ELEMENT_LIST = listOf(R.string.first, R.string.second, R.string.third)
val THREE_ELEMENT_COLOR_LIST = listOf(Color.Red, Color.Blue, Color.Green)

@Composable
fun RowScreen() {
  MyRow()

  BackButtonHandler {
    JetFundamentalsRouter.navigateTo(Screen.Navigation)
  }
}

@Composable
fun MyRow() {
  Row(
    verticalAlignment = Alignment.CenterVertically,
    horizontalArrangement = Arrangement.SpaceEvenly,
    modifier = Modifier.fillMaxSize()
  ) {
    THREE_ELEMENT_LIST.forEachIndexed { index, textResId  ->
      Text(
        text = stringResource(id = textResId),
        fontSize = 18.sp,
        modifier = Modifier.background(color = THREE_ELEMENT_COLOR_LIST[index]).weight(1 / 3f)
      )
    }
  }
}

@Composable
fun RowScope.MyRow(textResId: Int) {
  Text(
    text = stringResource(id = textResId),
    fontSize = 18.sp,
    modifier = Modifier.weight(1 / 3f)
  )
}
