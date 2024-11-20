package com.raywenderlich.jetpackcompose.screens

import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import com.raywenderlich.jetpackcompose.R
import com.raywenderlich.jetpackcompose.router.BackButtonHandler
import com.raywenderlich.jetpackcompose.router.JetFundamentalsRouter
import com.raywenderlich.jetpackcompose.router.Screen


@Composable
fun AlertDialogScreen() {

  MyAlertDialog()

  BackButtonHandler {
    JetFundamentalsRouter.navigateTo(Screen.Navigation)
  }
}

@Composable
fun MyAlertDialog() {
  val shouldShowDialog = remember { mutableStateOf(true) }

  if (shouldShowDialog.value) {
    AlertDialog(
      onDismissRequest = {
        shouldShowDialog.value = false
        JetFundamentalsRouter.navigateTo(Screen.Navigation)
      },
      title = { Text(text = stringResource(id = R.string.alert_dialog_text)) },
      text = { Text(text = stringResource(id = R.string.alert_dialog_text)) },
      confirmButton = {
        Button(
          colors = ButtonDefaults.buttonColors(
            backgroundColor = colorResource(id = R.color.colorPrimary)
          ),
          onClick = {
            shouldShowDialog.value = false
            JetFundamentalsRouter.navigateTo(Screen.Navigation)
          }
        ) {
          Text(
            text = stringResource(id = R.string.confirm),
            color = Color.White
          )
        }
      }
    )
  }
}