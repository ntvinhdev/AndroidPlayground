package com.yourcompany.android.jetnotes.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Checkbox
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.yourcompany.android.jetnotes.theme.green

@Composable
fun Note() {
  val backgroundShape: Shape = RoundedCornerShape(4.dp)
  Row(
    modifier = Modifier
      .padding(all = 8.dp)
      .shadow(elevation = 1.dp, shape = backgroundShape)
      .fillMaxWidth()
      .heightIn(min = 64.dp)
      .background(Color.White, backgroundShape)
  ) {
    NoteColor(
      color = green,
      size = 40.dp,
      border = 1.dp,
      modifier = Modifier
        // Modifier.align() belongs to RowScope
        .align(Alignment.CenterVertically)
        .padding(start = 16.dp, end = 16.dp)
    )
    Column(
      modifier = Modifier
        .weight(1f)
        // Modifier.align() belongs to RowScope
        .align(Alignment.CenterVertically)
    ) {
      Text(
        text = "Title",
        maxLines = 1,
        style = TextStyle(
          fontWeight = FontWeight.Normal,
          fontSize = 16.sp,
          letterSpacing = 0.15.sp
        )
      )
      Text(
        text = "Content",
        maxLines = 1,
        style = TextStyle(
          fontWeight = FontWeight.Normal,
          fontSize = 14.sp,
          letterSpacing = 0.25.sp
        )
      )
    }
    Checkbox(
      checked = false,
      onCheckedChange = {},
      modifier = Modifier
        .padding(start = 8.dp)
        // Modifier.align() belongs to RowScope
        .align(Alignment.CenterVertically)
    )
  }
}

@Preview
@Composable
private fun NotePreview() {
  Note()
}