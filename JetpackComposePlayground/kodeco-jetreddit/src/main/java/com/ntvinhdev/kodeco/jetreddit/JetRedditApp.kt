package com.ntvinhdev.kodeco.jetreddit

import android.annotation.SuppressLint
import android.content.Intent
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.padding
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.ScaffoldState
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.MailOutline
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.ntvinhdev.kodeco.jetreddit.appdrawer.AppDrawer
import com.ntvinhdev.kodeco.jetreddit.domain.model.PostModel
import com.ntvinhdev.kodeco.jetreddit.routing.Screen
import com.ntvinhdev.kodeco.jetreddit.screens.AddScreen
import com.ntvinhdev.kodeco.jetreddit.screens.ChatActivity
import com.ntvinhdev.kodeco.jetreddit.screens.ChooseCommunityScreen
import com.ntvinhdev.kodeco.jetreddit.screens.HomeScreen
import com.ntvinhdev.kodeco.jetreddit.screens.MyProfileScreen
import com.ntvinhdev.kodeco.jetreddit.screens.SubredditsScreen
import com.ntvinhdev.kodeco.jetreddit.theme.JetRedditTheme
import com.yourcompany.android.jetreddit.util.Tags
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun JetRedditApp(
  allPosts: List<PostModel>,
  myPosts: List<PostModel>,
  communities: List<String>,
  selectedCommunity: String,
  savePost: (post: PostModel) -> Unit,
  searchCommunities: (searchedText: String) -> Unit,
  communitySelected: (community: String) -> Unit,
) {
  JetRedditTheme {
    AppContent(
      allPosts,
      myPosts,
      communities,
      selectedCommunity,
      savePost,
      searchCommunities,
      communitySelected
    )
  }
}

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
private fun AppContent(
  allPosts: List<PostModel>,
  myPosts: List<PostModel>,
  communities: List<String>,
  selectedCommunity: String,
  savePost: (post: PostModel) -> Unit,
  searchCommunities: (searchedText: String) -> Unit,
  communitySelected: (community: String) -> Unit,
) {
  val scaffoldState: ScaffoldState = rememberScaffoldState()
  val coroutineScope: CoroutineScope = rememberCoroutineScope()
  val navController = rememberNavController()
  val navBackStackEntry by navController.currentBackStackEntryAsState()

  Crossfade(targetState = navBackStackEntry?.destination?.route) { route: String? ->

    Scaffold(
      topBar = getTopBar(Screen.fromRoute(route), scaffoldState, coroutineScope),
      drawerContent = {
        AppDrawer(
          onScreenSelected = { screen ->
            navController.navigate(screen.route) {
              popUpTo(navController.graph.findStartDestination().id) {
                saveState = true
              }
              launchSingleTop = true
              restoreState = true
            }
            coroutineScope.launch { scaffoldState.drawerState.close() }
          }
        )
      },
      scaffoldState = scaffoldState,
      bottomBar = {
        BottomNavigationComponent(navController = navController)
      },
      content = {
        MainScreenContainer(
          navController,
          Modifier.padding(bottom = 56.dp),
          allPosts,
          myPosts,
          communities,
          selectedCommunity,
          savePost,
          searchCommunities,
          communitySelected
        )
      }
    )
  }
}

fun getTopBar(
  screenState: Screen,
  scaffoldState: ScaffoldState,
  coroutineScope: CoroutineScope
): @Composable (() -> Unit) {
  if (screenState == Screen.MyProfile || screenState == Screen.ChooseCommunity) {
    return {}
  } else {
    return {
      TopAppBar(
        screen = screenState,
        scaffoldState = scaffoldState,
        coroutineScope = coroutineScope
      )
    }
  }
}

/**
 * Represents top app bar on the screen
 */
@Composable
fun TopAppBar(
  screen: Screen,
  scaffoldState: ScaffoldState,
  coroutineScope: CoroutineScope
) {

  val context = LocalContext.current
  val colors = MaterialTheme.colors

  TopAppBar(
    title = {
      Text(
        text = stringResource(screen.titleResId),
        color = colors.primaryVariant
      )
    },
    backgroundColor = colors.surface,
    navigationIcon = {
      IconButton(
        modifier = Modifier.testTag(Tags.ACCOUNT_BUTTON),
        onClick = {
          coroutineScope.launch { scaffoldState.drawerState.open() }
        }) {
        Icon(
          Icons.Filled.AccountCircle,
          tint = Color.LightGray,
          contentDescription = stringResource(id = R.string.account)
        )
      }
    },
    actions = {
      if (screen == Screen.Home) {
        IconButton(
          modifier = Modifier.testTag(Tags.CHAT_BUTTON),
          onClick = {
            context.startActivity(Intent(context, ChatActivity::class.java))
          }) {
          Icon(
            Icons.Filled.MailOutline,
            tint = Color.LightGray,
            contentDescription = "Chat Icon"
          )
        }
      }
    }
  )
}

@Composable
private fun MainScreenContainer(
  navController: NavHostController,
  modifier: Modifier = Modifier,
  allPosts: List<PostModel>,
  myPosts: List<PostModel>,
  communities: List<String>,
  selectedCommunity: String,
  savePost: (post: PostModel) -> Unit,
  searchCommunities: (searchedText: String) -> Unit,
  communitySelected: (community: String) -> Unit,
) {
  Surface(
    modifier = modifier,
    color = MaterialTheme.colors.background
  ) {
    NavHost(
      navController = navController,
      startDestination = Screen.Home.route
    ) {
      composable(Screen.Home.route) {
        HomeScreen(allPosts)
      }

      composable(Screen.Subscriptions.route) {
        SubredditsScreen()
      }

      composable(Screen.NewPost.route) {
        AddScreen(selectedCommunity, navController) {
          savePost(it)
        }
      }

      composable(Screen.MyProfile.route) {
        MyProfileScreen(myPosts) { navController.popBackStack() }
      }

      composable(Screen.ChooseCommunity.route) {
        ChooseCommunityScreen(
          communities = communities,
          searchCommunities = { searchedText ->
            searchCommunities(searchedText)
          },
          communitySelected = { community ->
            communitySelected(community)
          }
        ) { navController.popBackStack() }
      }
    }
  }
}

@Composable
private fun BottomNavigationComponent(
  modifier: Modifier = Modifier,
  navController: NavHostController
) {
  var selectedItem by remember { mutableStateOf(0) }

  val items = listOf(
    NavigationItem(0, R.drawable.ic_baseline_home_24, R.string.home_icon, Screen.Home),
    NavigationItem(
      1,
      R.drawable.ic_baseline_format_list_bulleted_24,
      R.string.subscriptions_icon,
      Screen.Subscriptions
    ),
    NavigationItem(2, R.drawable.ic_baseline_add_24, R.string.post_icon, Screen.NewPost),
  )
  BottomNavigation(modifier = modifier) {
    items.forEach {
      BottomNavigationItem(
        modifier = Modifier.testTag(it.screen.route),
        icon = {
          Icon(
            imageVector = ImageVector.vectorResource(id = it.vectorResourceId),
            contentDescription = stringResource(id = it.contentDescriptionResourceId)
          )
        },
        selected = selectedItem == it.index,
        onClick = {
          selectedItem = it.index
          navController.navigate(it.screen.route) {
            popUpTo(navController.graph.findStartDestination().id) {
              saveState = true
            }
            launchSingleTop = true
            restoreState = true
          }
        }
      )
    }
  }
}

private data class NavigationItem(
  val index: Int,
  val vectorResourceId: Int,
  val contentDescriptionResourceId: Int,
  val screen: Screen
)