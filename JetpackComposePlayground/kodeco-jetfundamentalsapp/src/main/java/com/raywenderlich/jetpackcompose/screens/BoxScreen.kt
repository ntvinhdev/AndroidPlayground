package com.raywenderlich.jetpackcompose.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
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

@Composable
fun BoxScreen() {
  MyBox()

  BackButtonHandler {
    JetFundamentalsRouter.navigateTo(Screen.Navigation)
  }
}

@Composable
fun MyBox(
  modifier: Modifier = Modifier,
  contentModifier: Modifier = Modifier
) {
  Box(modifier = modifier.fillMaxSize()) {
    Text(
      text = stringResource(id = R.string.first),
      fontSize = 22.sp,
      modifier = contentModifier.align(Alignment.TopStart)
        .background(color = Color.Red)
    )
    Text(
      text = stringResource(id = R.string.second),
      fontSize = 22.sp,
      modifier = contentModifier.align(Alignment.Center)
        .background(color = Color.Blue)
    )
    Text(
      text = stringResource(id = R.string.third),
      fontSize = 22.sp,
      modifier = contentModifier.align(Alignment.BottomEnd)
        .background(color = Color.Green)
    )
  }
}
