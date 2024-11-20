package com.raywenderlich.jetpackcompose.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import com.raywenderlich.jetpackcompose.R
import com.raywenderlich.jetpackcompose.router.BackButtonHandler
import com.raywenderlich.jetpackcompose.router.JetFundamentalsRouter
import com.raywenderlich.jetpackcompose.router.Screen

@Composable
fun SurfaceScreen(modifier: Modifier = Modifier) {

  Box(modifier = modifier.fillMaxSize()) {
    MySurface(modifier = modifier.align(Alignment.Center))
  }

  BackButtonHandler {
    JetFundamentalsRouter.navigateTo(Screen.Navigation)
  }
}

@Composable
fun MySurface(modifier: Modifier) {
  Surface(
    modifier = modifier.size(200.dp),
    color = Color.LightGray,
    contentColor = colorResource(id = R.color.colorPrimary),
    elevation = 1.dp,
    border = BorderStroke(1.dp, Color.Black)
  ) {
    MyColumn()
  }
}