package com.ntvinhdev.android.edgetoedge

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.marginBottom
import androidx.core.view.marginRight
import androidx.core.view.marginStart
import androidx.core.view.marginTop
import androidx.core.view.updateLayoutParams
import com.google.android.material.floatingactionbutton.FloatingActionButton

class EdgeToEdgeActivity : AppCompatActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    enableEdgeToEdge()
    setContentView(R.layout.activity_main)

//    applyEdgeToEdgeWithSystemBarsInsertsForRootView()

    applyEdgeToEdgeWithSystemBarsInsets()

    applyEdgeToEdgeWithDisplayCutoutInsets()

    applyEdgeToEdeWithSystemGesturesInsets()

  }

  private fun applyEdgeToEdgeWithSystemBarsInsertsForRootView() {
    val rootView = findViewById<View>(R.id.main)
    ViewCompat.setOnApplyWindowInsetsListener(rootView) { v, insets ->
      val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
      v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
      insets
    }
  }

  private fun applyEdgeToEdeWithSystemGesturesInsets() {
    val systemGestureText = findViewById<TextView>(R.id.tvSystemGesture)
    ViewCompat.setOnApplyWindowInsetsListener(systemGestureText) { view, windowInsets ->
      val typeMask = WindowInsetsCompat.Type.systemGestures()
      val insets = windowInsets.getInsets(typeMask)

      view.updateLayoutParams<ConstraintLayout.LayoutParams> {
        leftMargin = insets.left
        rightMargin = insets.right
      }

      WindowInsetsCompat.CONSUMED
    }
  }

  private fun applyEdgeToEdgeWithDisplayCutoutInsets() {
    val text = findViewById<TextView>(R.id.textView)
    ViewCompat.setOnApplyWindowInsetsListener(text) { view, windowInsets ->
      val typeMask = WindowInsetsCompat.Type.systemBars() or WindowInsetsCompat.Type.displayCutout()
      val insets = windowInsets.getInsets(typeMask)

      val top = view.marginTop
      val start = view.marginStart
      view.updateLayoutParams<ConstraintLayout.LayoutParams> {
        topMargin = insets.top + top
        leftMargin = insets.left + start
        rightMargin = insets.right
      }
      WindowInsetsCompat.CONSUMED
    }
  }

  private fun applyEdgeToEdgeWithSystemBarsInsets() {
    val fab = findViewById<FloatingActionButton>(R.id.fab)
    ViewCompat.setOnApplyWindowInsetsListener(fab) { view, windowInsets ->
      val insets = windowInsets.getInsets(WindowInsetsCompat.Type.systemBars())

      val bottom = view.marginBottom
      val end = view.marginRight
      view.updateLayoutParams<ConstraintLayout.LayoutParams> {
        leftMargin = insets.left
        rightMargin = insets.right + end
        bottomMargin = insets.bottom + bottom
      }
      WindowInsetsCompat.CONSUMED
    }
  }
}