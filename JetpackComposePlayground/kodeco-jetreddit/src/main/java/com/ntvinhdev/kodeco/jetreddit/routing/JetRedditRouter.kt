package com.ntvinhdev.kodeco.jetreddit.routing

import com.ntvinhdev.kodeco.jetreddit.R

/**
 * Class defining the screens we have in the app.
 *
 * These objects should match files we have in the screens package
 */
sealed class Screen(val titleResId: Int, val route: String) {
  object Home : Screen(R.string.home, "Home")
  object Subscriptions : Screen(R.string.subreddits, "Subreddits")
  object NewPost : Screen(R.string.new_post, "New Post")
  object MyProfile : Screen(R.string.my_profile, "My Profile")
  object ChooseCommunity : Screen(R.string.choose_community, "Choose a community")

  companion object {
    fun fromRoute(route: String?): Screen {
      return when (route) {
        Home.route -> Home
        Subscriptions.route -> Subscriptions
        NewPost.route -> NewPost
        MyProfile.route -> MyProfile
        ChooseCommunity.route -> ChooseCommunity
        else -> Home
      }
    }
  }
}