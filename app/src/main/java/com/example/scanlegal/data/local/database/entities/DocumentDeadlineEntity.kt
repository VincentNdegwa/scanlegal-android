package com.example.scanlegal.data.local.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "document_deadlines")
data class DocumentDeadlineEntity(
    @PrimaryKey
    val id: String,
    val documentId: String,
    val description: String,
    val deadlineDate: String,
    val isRecurring: Boolean = false,
    val notificationTriggered: Boolean,
    val createdAt: String
)
