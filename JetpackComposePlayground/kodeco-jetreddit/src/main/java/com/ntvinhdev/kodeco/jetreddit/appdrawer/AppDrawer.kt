package com.ntvinhdev.kodeco.jetreddit.appdrawer

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.filled.Star
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.ntvinhdev.kodeco.jetreddit.routing.Screen
import com.ntvinhdev.kodeco.jetreddit.theme.JetRedditThemeSettings
import com.ntvinhdev.kodeco.jetreddit.R

/**
 * Represents root composable for the app drawer used in screens
 */
@Composable
fun AppDrawer(
  modifier: Modifier = Modifier,
  onScreenSelected: (Screen) -> Unit
) {
  Column(
    modifier = modifier
      .fillMaxSize()
      .background(color = MaterialTheme.colors.surface)
  ) {
    AppDrawerHeader()
    AppDrawerBody(onScreenSelected)
    AppDrawerFooter(modifier)
  }
}

/**
 * Represents app drawer header with the icon and the app name
 */
@Composable
private fun AppDrawerHeader() {
  Column(
    modifier = Modifier.fillMaxWidth(),
    horizontalAlignment = Alignment.CenterHorizontally
  ) {
    Image(
      imageVector = Icons.Filled.AccountCircle,
      colorFilter = ColorFilter.tint(Color.LightGray),
      modifier = Modifier
        .padding(all = 16.dp)
        .size(50.dp),
      contentScale = ContentScale.Fit,
      alignment = Alignment.Center,
      contentDescription = stringResource(id = R.string.account)
    )
    Text(
      text = stringResource(id = R.string.default_username),
      color = MaterialTheme.colors.primaryVariant
    )
    ProfileInfo()
  }
  Divider(
    color = MaterialTheme.colors.onSurface.copy(alpha = .2f),
    modifier = Modifier.padding(
      start = 16.dp,
      end = 16.dp,
      top = 16.dp
    )
  )
}

@Composable
fun ProfileInfo(modifier: Modifier = Modifier) {
  ConstraintLayout(
    modifier = modifier
      .fillMaxWidth()
      .padding(top = 16.dp)
  ) {
    val (karmaItem, divider, ageItem) = createRefs()
    val colors = MaterialTheme.colors

    ProfileInfoItem(
      iconAsset = Icons.Filled.Star,
      amountResourceId = R.string.default_karma_amount,
      textResourceId = R.string.karma,
      modifier = modifier.constrainAs(karmaItem) {
        centerVerticallyTo(other = parent)
        start.linkTo(anchor = parent.start)
      }
    )

    Divider(
      modifier = modifier
        .width(width = 1.dp)
        .constrainAs(ref = divider) {
          centerVerticallyTo(other = karmaItem)
          centerHorizontallyTo(other = parent)
          height = Dimension.fillToConstraints
        },
      color = colors.onSurface.copy(alpha = .2f)
    )

    ProfileInfoItem(
      iconAsset = Icons.Filled.ShoppingCart,
      amountResourceId = R.string.default_reddit_age_amount,
      textResourceId = R.string.reddit_age,
      modifier = modifier.constrainAs(ref = ageItem) {
        start.linkTo(anchor = divider.end)
        centerVerticallyTo(other = parent)
      }
    )
  }
}

@Composable
private fun ProfileInfoItem(
  iconAsset: ImageVector,
  amountResourceId: Int,
  textResourceId: Int,
  modifier: Modifier
) {
  val colors = MaterialTheme.colors
  ConstraintLayout(modifier = modifier) {
    val (iconRef, amountRef, titleRef) = createRefs()
    val itemModifier = Modifier
    Icon(
      contentDescription = stringResource(id = textResourceId),
      imageVector = iconAsset,
      tint = Color.Blue,
      modifier = itemModifier
        .constrainAs(ref = iconRef) {
          centerVerticallyTo(other = parent)
          start.linkTo(anchor = parent.start)
        }.padding(start = 16.dp)
    )
    Text(
      text = stringResource(id = amountResourceId),
      color = colors.primaryVariant,
      fontSize = 10.sp,
      modifier = itemModifier
        .padding(start = 8.dp)
        .constrainAs(ref = amountRef) {
          top.linkTo(anchor = iconRef.top)
          start.linkTo(anchor = iconRef.end)
          bottom.linkTo(anchor = titleRef.top)
        }
    )
    Text(
      text = stringResource(textResourceId),
      color = Color.Gray,
      fontSize = 10.sp,
      modifier = itemModifier
        .padding(start = 8.dp)
        .constrainAs(ref = titleRef) {
          top.linkTo(anchor = amountRef.bottom)
          start.linkTo(anchor = iconRef.end)
          bottom.linkTo(anchor = iconRef.bottom)
        }
    )
  }
}

/**
 * Represents app drawer actions:
 * * screen navigation
 * * app light/dark mode
 */
@Composable
private fun AppDrawerBody(
  onScreenSelected: (Screen) -> Unit
) {
  Column {
    ScreenNavigationButton(
      icon = Icons.Filled.AccountBox,
      label = stringResource(id = R.string.my_profile),
      onClickAction = {
        onScreenSelected.invoke(Screen.MyProfile)
      }
    )
    ScreenNavigationButton(
      icon = Icons.Filled.Home,
      label = stringResource(R.string.saved),
      onClickAction = {
        onScreenSelected.invoke(Screen.Subscriptions)
      }
    )
  }
}

/**
 * Represents component in the app drawer that the user can use to change the screen
 */
@Composable
private fun ScreenNavigationButton(
  icon: ImageVector,
  label: String,
  onClickAction: () -> Unit,
  modifier: Modifier = Modifier
) {
  val colors = MaterialTheme.colors

  val surfaceModifier = modifier
    .padding(start = 8.dp, top = 8.dp, end = 8.dp)
    .fillMaxWidth()

  Surface(
    modifier = surfaceModifier,
    color = colors.surface,
    shape = MaterialTheme.shapes.small
  ) {
    TextButton(
      onClick = onClickAction,
      modifier = Modifier.fillMaxWidth()
    ) {
      Row(
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()
      ) {
        Image(
          imageVector = icon,
          colorFilter = ColorFilter.tint(Color.Gray),
          contentDescription = label
        )
        Spacer(Modifier.width(16.dp))
        Text(
          fontSize = 10.sp,
          text = label,
          style = MaterialTheme.typography.body2,
          color = colors.primaryVariant
        )
      }
    }
  }
}

/**
 * Represents setting component in the app drawer
 */
@Composable
private fun AppDrawerFooter(modifier: Modifier = Modifier) {
  ConstraintLayout(
    modifier = modifier
      .fillMaxSize()
      .padding(start = 16.dp, bottom = 16.dp, end = 16.dp)
  ) {
    val colors = MaterialTheme.colors
    val (settingsImage, settingsText, darkModeButton) = createRefs()
    Icon(
      modifier = modifier.constrainAs(ref = settingsImage) {
        start.linkTo(anchor = parent.start)
        bottom.linkTo(anchor = parent.bottom)
      },
      imageVector = Icons.Default.Settings,
      contentDescription = stringResource(id = R.string.settings),
      tint = colors.primaryVariant
    )
    Text(
      fontSize = 10.sp,
      text = stringResource(R.string.settings),
      style = MaterialTheme.typography.body2,
      color = colors.primaryVariant,
      modifier = modifier
        .padding(start = 16.dp)
        .constrainAs(ref = settingsText) {
          start.linkTo(anchor = settingsImage.end)
          centerVerticallyTo(other = settingsImage)
        }
    )
    Icon(
      imageVector = ImageVector.vectorResource(id = R.drawable.ic_moon),
      contentDescription = stringResource(id = R.string.change_theme),
      modifier = modifier
        .clickable { changeTheme() }
        .constrainAs(ref = darkModeButton) {
          end.linkTo(anchor = parent.end)
          bottom.linkTo(anchor = settingsImage.bottom)
        },
      tint = colors.primaryVariant
    )
  }
}

private fun changeTheme() {
  JetRedditThemeSettings.isInDarkTheme.value = JetRedditThemeSettings.isInDarkTheme.value.not()
}