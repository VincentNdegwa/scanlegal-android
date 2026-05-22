package com.example.scanlegal.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.scanlegal.data.local.database.converter.Converters
import com.example.scanlegal.data.local.database.dao.ChatDao
import com.example.scanlegal.data.local.database.dao.ChatSessionDao
import com.example.scanlegal.data.local.database.dao.CreditsLedgerDao
import com.example.scanlegal.data.local.database.dao.DocumentDao
import com.example.scanlegal.data.local.database.dao.ProfileDao
import com.example.scanlegal.data.local.database.dao.UserPreferencesDao
import com.example.scanlegal.data.local.database.entities.DocumentAnalysisFlagEntity
import com.example.scanlegal.data.local.database.entities.ChatMessageEntity
import com.example.scanlegal.data.local.database.entities.ChatSessionEntity
import com.example.scanlegal.data.local.database.entities.CreditsLedgerEntity
import com.example.scanlegal.data.local.database.entities.DocumentDeadlineEntity
import com.example.scanlegal.data.local.database.entities.DocumentEntity
import com.example.scanlegal.data.local.database.entities.ProfileEntity
import com.example.scanlegal.data.local.database.entities.UserPreferencesEntity

@Database(
    entities = [
        ProfileEntity::class,
        UserPreferencesEntity::class,
        DocumentEntity::class,
        DocumentAnalysisFlagEntity::class,
        DocumentDeadlineEntity::class,
        ChatSessionEntity::class,
        ChatMessageEntity::class,
        CreditsLedgerEntity::class
    ],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    
    abstract fun profileDao(): ProfileDao
    abstract fun userPreferencesDao(): UserPreferencesDao
    abstract fun documentDao(): DocumentDao
    abstract fun chatDao(): ChatDao
    abstract fun chatSessionDao(): ChatSessionDao
    abstract fun creditsLedgerDao(): CreditsLedgerDao
}
