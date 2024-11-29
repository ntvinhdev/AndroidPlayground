package com.yourcompany.android.jetnotes

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.activity.compose.setContent
import com.yourcompany.android.jetnotes.viewmodel.MainViewModel
import com.yourcompany.android.jetnotes.viewmodel.MainViewModelFactory

/**
 * Main activity for the app.
 */
class MainActivity : AppCompatActivity() {

  private val viewModel: MainViewModel by viewModels(factoryProducer = {
    MainViewModelFactory(
      this,
      (application as JetNotesApplication).dependencyInjector.repository
    )
  })

  @SuppressLint("UnusedMaterialScaffoldPaddingParameter")
  override fun onCreate(savedInstanceState: Bundle?) {
    // Switch to AppTheme for displaying the activity
    setTheme(R.style.Theme_JetNotes)

    super.onCreate(savedInstanceState)

    setContent {

    }
  }
}
