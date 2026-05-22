package com.example.scanlegal.data.local.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.scanlegal.data.local.database.entities.UserPreferencesEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface UserPreferencesDao {
    
    @Query("SELECT * FROM user_preferences WHERE userId = :userId")
    suspend fun getPreferencesByUserId(userId: String): UserPreferencesEntity?
    
    @Query("SELECT * FROM user_preferences WHERE userId = :userId")
    fun getPreferencesByUserIdFlow(userId: String): Flow<UserPreferencesEntity?>
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPreferences(preferences: UserPreferencesEntity)
    
    @Update
    suspend fun updatePreferences(preferences: UserPreferencesEntity)
    
    @Query("DELETE FROM user_preferences WHERE userId = :userId")
    suspend fun deletePreferences(userId: String)
}
