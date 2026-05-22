package com.example.scanlegal.data.local.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_preferences")
data class UserPreferencesEntity(
    @PrimaryKey
    val userId: String,
    val activeJurisdiction: String,
    val onboardingCompleted: Boolean = false,
    val preferredOutputLanguage: String = "en",
    val customPlaybookRules: String? = null,
    val updatedAt: String
)
