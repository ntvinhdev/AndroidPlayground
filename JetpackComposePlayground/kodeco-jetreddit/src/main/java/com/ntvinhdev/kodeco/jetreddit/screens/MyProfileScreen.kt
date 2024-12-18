package com.ntvinhdev.kodeco.jetreddit.screens

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.constraintlayout.compose.ConstraintLayout
import com.ntvinhdev.kodeco.jetreddit.viewmodel.MainViewModel

@Composable
fun MyProfileScreen(
  viewModel: MainViewModel,
  modifier: Modifier = Modifier,
  onBackSelected: () -> Unit) {
  ConstraintLayout() {
    createHorizontalChain()
  }
}