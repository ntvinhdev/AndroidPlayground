package com.yourcompany.android.jetnotes.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.yourcompany.android.jetnotes.data.database.model.ColorDbModel
import kotlinx.coroutines.flow.Flow

/**
 * Dao for managing Color table in the database.
 */
@Dao
interface ColorDao {

  @Query("SELECT * FROM ColorDbModel")
  fun getAll(): Flow<List<ColorDbModel>>

  @Query("SELECT * FROM ColorDbModel")
  suspend fun getAllSync(): List<ColorDbModel>

  @Query("SELECT * FROM ColorDbModel WHERE id LIKE :id")
  fun findById(id: Long): Flow<ColorDbModel>

  @Query("SELECT * FROM ColorDbModel WHERE id LIKE :id")
  suspend fun findByIdSync(id: Long): ColorDbModel

  @Insert
  suspend fun insertAll(vararg colorDbModels: ColorDbModel)
}