package com.yourcompany.android.jetreddit.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ntvinhdev.kodeco.jetreddit.R
import com.ntvinhdev.kodeco.jetreddit.components.JoinButton
import com.ntvinhdev.kodeco.jetreddit.domain.model.PostModel
import com.ntvinhdev.kodeco.jetreddit.domain.model.PostModel.Companion.DEFAULT_POST
import com.yourcompany.android.jetreddit.util.Tags

@Composable
fun TextPost(
  post: PostModel,
  onJoinButtonClick: (Boolean) -> Unit = {}
) {
  Post(post, onJoinButtonClick) {
    TextContent(post.text)
  }
}

@Composable
fun ImagePost(
  post: PostModel,
  onJoinButtonClick: (Boolean) -> Unit = {}
) {
  Post(post, onJoinButtonClick) {
    ImageContent(post.image ?: R.drawable.compose_course)
  }
}

@Composable
fun Post(
  post: PostModel,
  onJoinButtonClick: (Boolean) -> Unit = {},
  content: @Composable () -> Unit = {}
) {
  Card(shape = MaterialTheme.shapes.large) {
    Column(modifier = Modifier.padding(top = 8.dp, bottom = 8.dp)) {
      Header(post, onJoinButtonClick)
      Spacer(modifier = Modifier.height(4.dp))
      content.invoke()
      Spacer(modifier = Modifier.height(8.dp))
      PostActions(post)
    }
  }
}

@Composable
fun Header(
  post: PostModel,
  onJoinButtonClick: (Boolean) -> Unit = {}
) {
  Row(
    verticalAlignment = Alignment.CenterVertically,
    modifier = Modifier.padding(start = 16.dp)
  ) {
    Image(
      bitmap = ImageBitmap.imageResource(id = R.drawable.subreddit_placeholder),
      contentDescription = stringResource(id = R.string.subreddits),
      modifier = Modifier
        .size(40.dp)
        .clip(CircleShape)
    )
    Spacer(modifier = Modifier.width(8.dp))
    Column(modifier = Modifier.weight(1f)) {
      Text(
        text = stringResource(R.string.subreddit_header, post.subreddit),
        color = MaterialTheme.colors.primaryVariant
      )
      Text(
        text = stringResource(id = R.string.post_header, post.username, post.postedTime),
        color = Color.Gray
      )
    }
    Spacer(modifier = Modifier.width(4.dp))
    JoinButton(onJoinButtonClick)
    MoreActionsMenu()
  }
  Title(text = post.title)
}

@Composable
fun MoreActionsMenu() {
  var expanded by remember { mutableStateOf(false) }
  Box(modifier = Modifier.wrapContentSize(Alignment.TopStart)) {

    IconButton(onClick = { expanded = true }) {
      Icon(
        imageVector = Icons.Default.MoreVert,
        tint = Color.DarkGray,
        contentDescription = stringResource(id = R.string.more_actions)
      )
    }

    DropdownMenu(
      expanded = expanded,
      onDismissRequest = { expanded = false }
    ) {
      CustomDropdownMenuItem(
        vectorResourceId = R.drawable.ic_baseline_bookmark_24,
        text = stringResource(id = R.string.save)
      )
    }
  }
}

@Composable
fun CustomDropdownMenuItem(
  @DrawableRes vectorResourceId: Int,
  color: Color = Color.Black,
  text: String,
  onClickAction: () -> Unit = {}
) {
  DropdownMenuItem(onClick = onClickAction) {
    Row(verticalAlignment = Alignment.CenterVertically) {
      Icon(
        imageVector = ImageVector.vectorResource(id = vectorResourceId),
        tint = color,
        contentDescription = stringResource(id = R.string.save)
      )
      Spacer(modifier = Modifier.width(8.dp))
      Text(text = text, fontWeight = FontWeight.Medium, color = color)
    }
  }
}

@Composable
fun Title(text: String) {
  Text(
    text = text,
    maxLines = 3,
    fontWeight = FontWeight.Medium,
    fontSize = 16.sp,
    color = MaterialTheme.colors.primaryVariant,
    modifier = Modifier.padding(start = 16.dp, end = 16.dp)
  )
}

@Composable
fun TextContent(text: String) {
  Text(
    modifier = Modifier.padding(
      start = 16.dp,
      end = 16.dp
    ),
    text = text,
    color = Color.Gray,
    fontSize = 12.sp,
    maxLines = 3
  )
}

@Composable
fun ImageContent(image: Int) {
  val imageAsset = ImageBitmap.imageResource(id = image)
  Image(
    bitmap = imageAsset,
    contentDescription = stringResource(id = R.string.post_header_description),
    modifier = Modifier
      .fillMaxWidth()
      .aspectRatio(imageAsset.width.toFloat() / imageAsset.height)
      .testTag(Tags.POST_IMAGE),
    contentScale = ContentScale.Crop
  )
}

@Composable
fun PostActions(post: PostModel) {
  Row(
    modifier = Modifier
      .fillMaxWidth()
      .padding(start = 16.dp, end = 16.dp),
    horizontalArrangement = Arrangement.SpaceBetween,
    verticalAlignment = Alignment.CenterVertically
  ) {
    VotingAction(text = post.likes, onUpVoteAction = {}, onDownVoteAction = {})
    PostAction(
      vectorResourceId = R.drawable.ic_baseline_comment_24,
      text = post.comments,
      onClickAction = {}
    )
    PostAction(
      vectorResourceId = R.drawable.ic_baseline_share_24,
      text = stringResource(R.string.share),
      onClickAction = {}
    )
    PostAction(
      vectorResourceId = R.drawable.ic_baseline_emoji_events_24,
      text = stringResource(R.string.award),
      onClickAction = {}
    )
  }
}

@Composable
fun VotingAction(
  text: String,
  onUpVoteAction: () -> Unit,
  onDownVoteAction: () -> Unit
) {
  Row(verticalAlignment = Alignment.CenterVertically) {
    ArrowButton(
      onClickAction = onUpVoteAction,
      arrowResourceId = R.drawable.ic_baseline_arrow_upward_24
    )
    Text(
      text = text,
      color = Color.Gray,
      fontWeight = FontWeight.Medium,
      fontSize = 12.sp
    )
    ArrowButton(
      onClickAction = onDownVoteAction,
      arrowResourceId = R.drawable.ic_baseline_arrow_downward_24
    )
  }
}

@Composable
fun ArrowButton(onClickAction: () -> Unit, arrowResourceId: Int) {
  IconButton(onClick = onClickAction, modifier = Modifier.size(30.dp)) {
    Icon(
      imageVector = ImageVector.vectorResource(id = arrowResourceId),
      contentDescription = stringResource(R.string.upvote),
      modifier = Modifier.size(20.dp),
      tint = Color.Gray
    )
  }
}

@Composable
fun PostAction(
  @DrawableRes vectorResourceId: Int,
  text: String,
  onClickAction: () -> Unit
) {
  Box(modifier = Modifier.clickable(onClick = onClickAction)) {
    Row(verticalAlignment = Alignment.CenterVertically) {
      Icon(
        ImageVector.vectorResource(id = vectorResourceId),
        contentDescription = stringResource(id = R.string.post_action),
        tint = Color.Gray,
        modifier = Modifier.size(20.dp)
      )
      Spacer(modifier = Modifier.width(4.dp))
      Text(text = text, fontWeight = FontWeight.Medium, color = Color.Gray, fontSize = 12.sp)
    }
  }
}

@Preview
@Composable
fun ArrowButtonPreview() {
  ArrowButton({}, R.drawable.ic_baseline_arrow_upward_24)
}

@Preview
@Composable
fun HeaderPreview() {
  Column {
    Header(DEFAULT_POST)
  }
}

@Preview
@Composable
fun VotingActionPreview() {
  VotingAction("555", {}, {})
}

@Preview
@Composable
fun PostPreview() {
  Post(DEFAULT_POST)
}

@Preview
@Composable
fun TextPostPreview() {
  Post(DEFAULT_POST) {
    TextContent(DEFAULT_POST.text)
  }
}

@Preview
@Composable
fun ImagePostPreview() {
  Post(DEFAULT_POST) {
    ImageContent(DEFAULT_POST.image ?: R.drawable.compose_course)
  }
}