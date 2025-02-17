package com.ntvinhdev.kodeco.jetreddit.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.ntvinhdev.kodeco.jetreddit.R
import com.ntvinhdev.kodeco.jetreddit.domain.model.PostModel
import com.ntvinhdev.kodeco.jetreddit.routing.Screen

@Composable
fun AddScreen(
  selectedCommunity: String,
  navHostController: NavHostController,
  savePost: (post: PostModel) -> Unit
) {

  var post by remember { mutableStateOf(PostModel.EMPTY) }

  Column(modifier = Modifier.fillMaxSize()) {
    CommunityPicker(selectedCommunity, navHostController)
    TitleTextField(post.title) {
      newTitle -> post = post.copy(title = newTitle)
    }
    BodyTextField(post.text) {
      newContent -> post = post.copy(text = newContent)
    }
    AddPostButton(selectedCommunity.isNotEmpty() && post.title.isNotEmpty()) {
      savePost(post)
      navHostController.popBackStack()
    }
  }
}

/**
 * Input view for the post title
 */
@Composable
private fun TitleTextField(text: String, onTextChange: (String) -> Unit) {
  val activeColor = MaterialTheme.colors.onSurface

  TextField(
    value = text,
    onValueChange = onTextChange,
    label = { Text(stringResource(R.string.title)) },
    modifier = Modifier
      .fillMaxWidth()
      .padding(horizontal = 8.dp),
    colors = TextFieldDefaults.outlinedTextFieldColors(
      focusedBorderColor = activeColor,
      focusedLabelColor = activeColor,
      cursorColor = activeColor,
      backgroundColor = MaterialTheme.colors.surface
    )
  )
}

/**
 * Input view for the post body
 */
@Composable
private fun BodyTextField(text: String, onTextChange: (String) -> Unit) {
  val activeColor = MaterialTheme.colors.onSurface

  TextField(
    value = text,
    onValueChange = onTextChange,
    label = { Text(stringResource(R.string.body_text)) },
    modifier = Modifier
      .fillMaxWidth()
      .heightIn(max = 240.dp)
      .padding(horizontal = 8.dp)
      .padding(top = 16.dp),
    colors = TextFieldDefaults.outlinedTextFieldColors(
      focusedBorderColor = activeColor,
      focusedLabelColor = activeColor,
      cursorColor = activeColor,
      backgroundColor = MaterialTheme.colors.surface
    )
  )
}

/**
 * Input view for the post body
 */
@Composable
private fun AddPostButton(isEnabled: Boolean, onSaveClicked: () -> Unit) {
  Button(
    onClick = onSaveClicked,
    enabled = isEnabled,
    content = {
      Text(
        text = stringResource(R.string.save_post),
        color = MaterialTheme.colors.onSurface
      )
    },
    modifier = Modifier
      .fillMaxWidth()
      .heightIn(max = 240.dp)
      .padding(horizontal = 8.dp)
      .padding(top = 16.dp),
  )
}

@Composable
private fun CommunityPicker(
  selectedCommunity: String,
  navHostController: NavHostController
) {

  val selectedText =
    if (selectedCommunity.isEmpty()) stringResource(R.string.choose_community) else selectedCommunity

  Row(
    modifier = Modifier
      .fillMaxWidth()
      .heightIn(max = 240.dp)
      .padding(horizontal = 8.dp)
      .padding(top = 16.dp)
      .clickable {
        navHostController.navigate(Screen.ChooseCommunity.route)
      },
  ) {
    Image(
      bitmap = ImageBitmap.imageResource(id = R.drawable.subreddit_placeholder),
      contentDescription = stringResource(id = R.string.subreddits),
      modifier = Modifier
        .size(24.dp)
        .clip(CircleShape)
    )

    Text(
      text = selectedText,
      modifier = Modifier.padding(start = 8.dp)
    )
  }
}