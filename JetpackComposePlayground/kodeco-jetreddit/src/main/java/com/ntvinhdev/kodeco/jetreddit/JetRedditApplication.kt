package com.ntvinhdev.kodeco.jetreddit

import android.app.Application
import com.ntvinhdev.kodeco.jetreddit.dependencyinjection.DependencyInjector

class JetRedditApplication : Application() {

  private lateinit var dependencyInjector: DependencyInjector

  override fun onCreate() {
    super.onCreate()
    initDependencyInjector()
  }

  private fun initDependencyInjector() {
    dependencyInjector = DependencyInjector(this)
  }
}