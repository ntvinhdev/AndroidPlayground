package com.raywenderlich.jetpackcompose.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.sp
import com.raywenderlich.jetpackcompose.router.BackButtonHandler
import com.raywenderlich.jetpackcompose.router.JetFundamentalsRouter
import com.raywenderlich.jetpackcompose.router.Screen

@Composable
fun ColumnScreen() {
  MyColumn()

  BackButtonHandler {
    JetFundamentalsRouter.navigateTo(Screen.Navigation)
  }
}

@Composable
fun MyColumn() {
  Column(
    horizontalAlignment = Alignment.CenterHorizontally,
    verticalArrangement = Arrangement.SpaceEvenly,
    modifier = Modifier.fillMaxSize()
  ) {
    THREE_ELEMENT_LIST.forEachIndexed { index, textResId ->
      Text(
        text = stringResource(id = textResId),
        fontSize = 22.sp,
        modifier = Modifier.background(color = THREE_ELEMENT_COLOR_LIST[index])
      )
    }
  }
}