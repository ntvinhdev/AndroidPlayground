package com.ntvinhdev.kodeco.jetreddit.components

import androidx.compose.animation.animateColor
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.yourcompany.android.jetreddit.util.Tags

@Composable
fun JoinButton(onClick: (Boolean) -> Unit = {}) {
  var buttonState: JoinButtonState by remember { mutableStateOf(JoinButtonState.IDLE) }

  val shape = RoundedCornerShape(corner = CornerSize(size = 12.dp))
  val transition = updateTransition(
    targetState = buttonState,
    label = "JoinButtonTransition"
  )
//  val buttonBackgroundColor: Color =
//    if (buttonState == JoinButtonState.PRESSED) Color.White else Color.Blue
//  val buttonBackgroundColor: Color by animateColorAsState(
//    if (buttonState == JoinButtonState.PRESSED) Color.White else Color.Blue
//  )
  val duration = 600
  val buttonBackgroundColor: Color by transition.animateColor(
    transitionSpec = { tween(duration) },
    label = "Button Background Color"
  ) { state ->
    when (state) {
      JoinButtonState.IDLE -> Color.Blue
      JoinButtonState.PRESSED -> Color.White
    }
  }
  val buttonWidth: Dp by transition.animateDp(
    transitionSpec = { tween(duration) },
    label = "Button Width"
  ) { state ->
    when (state) {
      JoinButtonState.IDLE -> 70.dp
      JoinButtonState.PRESSED -> 32.dp
    }
  }
  val textMaxWidth: Dp by transition.animateDp(
    transitionSpec = { tween(duration) },
    label = "Text Max Width"
  ) { state ->
    when(state) {
      JoinButtonState.IDLE -> 40.dp
      JoinButtonState.PRESSED -> 0.dp
    }
  }

  val iconAsset: ImageVector =
    if (buttonState == JoinButtonState.PRESSED) Icons.Default.Check else Icons.Default.Add
//  val iconTintColor: Color =
//    if (buttonState == JoinButtonState.PRESSED) Color.Blue else Color.White
  val iconTintColor: Color by transition.animateColor(
    transitionSpec = { tween(duration) },
    label = "Icon Tint Color"
  ) { state ->
    when (state) {
      JoinButtonState.IDLE -> Color.White
      JoinButtonState.PRESSED -> Color.Blue
    }
  }

  Box(
    contentAlignment = Alignment.Center,
    modifier = Modifier
      .clip(shape)
      .border(width = 1.dp, color = Color.Blue, shape = shape)
      .background(color = buttonBackgroundColor)
      .size(width = buttonWidth, height = 24.dp)
      .clickable(onClick = {
        buttonState = if (buttonState == JoinButtonState.IDLE) {
          onClick.invoke(true)
          JoinButtonState.PRESSED
        } else {
          onClick.invoke(false)
          JoinButtonState.IDLE
        }
      })
      .testTag(Tags.JOIN_BUTTON)
  ) {
    Row(verticalAlignment = Alignment.CenterVertically) {
      Icon(
        imageVector = iconAsset,
        contentDescription = "Plus Icon",
        tint = iconTintColor,
        modifier = Modifier.size(16.dp)
      )
      Text(
        text = "Join",
        color = Color.White,
        fontSize = 14.sp,
        maxLines = 1,
        modifier = Modifier.widthIn(
          min = 0.dp,
          max = textMaxWidth
        )
      )
    }
  }
}

enum class JoinButtonState {
  IDLE,
  PRESSED
}

@Preview
@Composable
fun JoinButtonPreview() {
  JoinButton(onClick = {})
}