package com.ntvinhdev.android.edgetoedge

import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsAnimationCompat
import androidx.core.view.WindowInsetsAnimationCompat.Callback.DISPATCH_MODE_STOP
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ntvinhdev.android.edgetoedge.components.CustomAdapter

class SynchronizedKeyboardAnimationActivity : AppCompatActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    enableEdgeToEdge()

    setContentView(R.layout.activity_synchronized_keyboard_animation)

    val list: ArrayList<String> = arrayListOf()
    for (i in 0..40) {
      list.add("hello world, $i")
    }

    val rootView = findViewById<View>(R.id.main)
    val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
    val editText = findViewById<EditText>(R.id.editText)

    val adapter = CustomAdapter(list)
    recyclerView.layoutManager = LinearLayoutManager(this)
    recyclerView.adapter = adapter

    ViewCompat.setOnApplyWindowInsetsListener(rootView) { v, insets ->
      val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
      val imeVisible = insets.isVisible(WindowInsetsCompat.Type.ime())
      val ime = insets.getInsets(WindowInsetsCompat.Type.ime())

      v.setPadding(
        systemBars.left,
        systemBars.top,
        systemBars.right,
        if (imeVisible) ime.bottom else systemBars.bottom
      )
      insets
    }

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {

      var startBottom = 0f
      var endBottom = 0f
      ViewCompat.setWindowInsetsAnimationCallback(
        rootView,
        object : WindowInsetsAnimationCompat.Callback(DISPATCH_MODE_STOP) {

          override fun onPrepare(
            animation: WindowInsetsAnimationCompat
          ) {
            startBottom = editText.bottom.toFloat()
          }

          override fun onStart(
            animation: WindowInsetsAnimationCompat,
            bounds: WindowInsetsAnimationCompat.BoundsCompat
          ): WindowInsetsAnimationCompat.BoundsCompat {
            // Record the position of the view after the IME transition.
            endBottom = editText.bottom.toFloat()
            return bounds
          }

          override fun onEnd(animation: WindowInsetsAnimationCompat) {
            super.onEnd(animation)
          }

          override fun onProgress(
            insets: WindowInsetsCompat,
            runningAnimations: MutableList<WindowInsetsAnimationCompat>
          ): WindowInsetsCompat {
            Log.d("HELLOWORLD", "onProgress: ")
            // Find an IME animation.
            val imeAnimation = runningAnimations.find {
              it.typeMask and WindowInsetsCompat.Type.ime() != 0
            } ?: return insets

            // Offset the view based on the interpolated fraction of the IME animation.
            rootView.translationY =
              (startBottom - endBottom) * (1 - imeAnimation.interpolatedFraction)
            return insets
          }
        }
      )
    }
  }
}
