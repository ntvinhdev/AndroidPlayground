package com.ntvinhdev.kodeco.jetreddit.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ntvinhdev.kodeco.jetreddit.R
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.delay

private const val SEARCH_DELAY_MILLIS = 300L

private val defaultCommunities = listOf("kodeco", "androiddev", "puppies")

@Composable
fun ChooseCommunityScreen(
  modifier: Modifier = Modifier,
  communities: List<String>,
  searchCommunities: (searchedText: String) -> Unit,
  communitySelected: (community: String) -> Unit,
  onBackSelected: () -> Unit
) {
  val scope = rememberCoroutineScope()
  var searchedText by remember { mutableStateOf("") }
  var currentJob by remember { mutableStateOf<Job?>(null) }
  val activeColor = MaterialTheme.colors.onSurface

  LaunchedEffect(Unit) {
    searchCommunities(searchedText)
  }

  Column {
    ChooseCommunityTopBar(onBackSelected = onBackSelected)
    TextField(
      value = searchedText,
      onValueChange = {
        searchedText = it
        currentJob?.cancel()
        currentJob = scope.async {
          delay(SEARCH_DELAY_MILLIS)
          searchCommunities(searchedText)
        }
      },
      leadingIcon = {
        Icon(
          imageVector = Icons.Default.Search,
          contentDescription = stringResource(R.string.search)
        )
      },
      label = { Text(stringResource(R.string.search)) },
      modifier = modifier
        .fillMaxWidth()
        .padding(horizontal = 8.dp),
      colors = TextFieldDefaults.outlinedTextFieldColors(
        focusedBorderColor = activeColor,
        focusedLabelColor = activeColor,
        cursorColor = activeColor,
        backgroundColor = MaterialTheme.colors.surface
      )
    )
    SearchedCommunities(communities, communitySelected, modifier, onBackSelected)
  }
}

@Composable
fun SearchedCommunities(
  communities: List<String>,
  communitySelected: (community: String) -> Unit,
  modifier: Modifier = Modifier,
  onBackSelected: () -> Unit
) {
  communities.forEach {
    Community(
      text = it,
      modifier = modifier,
      onCommunityClicked = {
        communitySelected(it)
        onBackSelected.invoke()
      }
    )
  }
}

@Composable
fun ChooseCommunityTopBar(
  modifier: Modifier = Modifier,
  onBackSelected: () -> Unit
) {

  val colors = MaterialTheme.colors

  TopAppBar(
    title = {
      Text(
        fontSize = 16.sp,
        text = stringResource(R.string.choose_community),
        color = colors.primaryVariant
      )
    },
    navigationIcon = {
      IconButton(
        onClick = { onBackSelected.invoke() }
      ) {
        Icon(
          imageVector = Icons.Default.Close,
          tint = colors.primaryVariant,
          contentDescription = stringResource(id = R.string.close)
        )
      }
    },
    backgroundColor = colors.primary,
    elevation = 0.dp,
    modifier = modifier
      .height(48.dp)
      .background(Color.Blue)
  )
}

@Preview
@Composable
fun SearchedCommunitiesPreview() {
  Column {
    SearchedCommunities(defaultCommunities, { }, Modifier) {}
  }
}