package com.yourcompany.android.jetnotes.dependencyinjection

import android.content.Context
import androidx.room.Room
import com.yourcompany.android.jetnotes.data.database.AppDatabase
import com.yourcompany.android.jetnotes.data.database.dbmapper.DbMapper
import com.yourcompany.android.jetnotes.data.database.dbmapper.DbMapperImpl
import com.yourcompany.android.jetnotes.data.repository.Repository
import com.yourcompany.android.jetnotes.data.repository.RepositoryImpl

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
    val noteDao = database.noteDao()
    val colorDao = database.colorDao()

    return RepositoryImpl(noteDao, colorDao, dbMapper)
  }
}
