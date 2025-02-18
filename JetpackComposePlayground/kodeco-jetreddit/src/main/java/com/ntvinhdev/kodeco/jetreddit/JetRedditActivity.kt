package com.ntvinhdev.kodeco.jetreddit

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import com.ntvinhdev.kodeco.jetreddit.viewmodel.MainViewModel
import com.ntvinhdev.kodeco.jetreddit.viewmodel.MainViewModelFactory

class JetRedditActivity : AppCompatActivity() {

  private val viewModel: MainViewModel by viewModels(factoryProducer = {
    MainViewModelFactory(
      this,
      (application as JetRedditApplication).dependencyInjector.repository
    )
  })

  override fun onCreate(savedInstanceState: Bundle?) {
    // Switch to AppTheme for displaying the activity
    setTheme(R.style.Theme_JetReddit)

    super.onCreate(savedInstanceState)

    setContent {
      val allPosts by viewModel.allPosts.observeAsState(listOf())
      val myPosts by viewModel.myPosts.observeAsState(listOf())
      val communities by viewModel.subreddits.observeAsState(listOf())
      val selectedCommunity by viewModel.selectedCommunity.observeAsState("")

      JetRedditApp(
        allPosts,
        myPosts,
        communities,
        selectedCommunity,
        savePost = { post ->
          viewModel.savePost(post)
        },
        searchCommunities = { searchedText ->
          viewModel.searchCommunities(searchedText)
        },
        communitySelected = { community ->
          viewModel.selectedCommunity.postValue(community)
        }
      )
    }
  }
}