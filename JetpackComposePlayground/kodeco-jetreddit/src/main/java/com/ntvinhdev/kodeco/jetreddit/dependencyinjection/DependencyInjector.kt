package com.ntvinhdev.kodeco.jetreddit.dependencyinjection

import android.content.Context
import androidx.room.Room
import com.ntvinhdev.kodeco.jetreddit.data.database.AppDatabase
import com.ntvinhdev.kodeco.jetreddit.data.database.dbmapper.DbMapper
import com.ntvinhdev.kodeco.jetreddit.data.database.dbmapper.DbMapperImpl
import com.ntvinhdev.kodeco.jetreddit.data.repository.RepositoryImpl
import com.yourcompany.android.jetreddit.data.repository.Repository

/**
 * Provides dependencies across the app.
 */
class DependencyInjector(applicationContext: Context) {

  val repository: Repository by lazy { provideRepository(database) }

  private val database: AppDatabase by lazy { provideDatabase(applicationContext) }
  private val dbMapper: DbMapper = DbMapperImpl()

  private fun provideDatabase(applicationContext: Context): AppDatabase =
    Room.databaseBuilder(
      applicationContext,
      AppDatabase::class.java,
      AppDatabase.DATABASE_NAME
    ).build()

  private fun provideRepository(database: AppDatabase): Repository {
    val postDao = database.postDao()

    return RepositoryImpl(postDao, dbMapper)
  }
}
