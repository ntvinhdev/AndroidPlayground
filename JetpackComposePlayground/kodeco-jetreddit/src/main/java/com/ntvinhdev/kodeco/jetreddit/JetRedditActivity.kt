package com.ntvinhdev.kodeco.jetreddit

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
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
      JetRedditApp(viewModel)
    }
  }
}