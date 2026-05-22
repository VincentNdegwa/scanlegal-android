package com.example.scanlegal.data.local.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "profiles")
data class ProfileEntity(
    @PrimaryKey
    val id: String,
    val email: String,
    val userTier: String = "FREE",
    val isActive: Boolean = true,
    val createdAt: String,
    val updatedAt: String
)
