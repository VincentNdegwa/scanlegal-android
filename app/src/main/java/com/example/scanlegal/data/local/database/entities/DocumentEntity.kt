package com.example.scanlegal.data.local.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.scanlegal.domain.model.enums.ProcessingStatus

@Entity(tableName = "documents")
data class DocumentEntity(
    @PrimaryKey
    val id: String,
    val userId: String,
    val title: String,
    val documentType: String? = null,
    val pageCount: Int,
    val storageProvider: String = "firebase",
    val storageKey: String,
    val fileHash: String,
    val fileSizeBytes: Long,
    val status: ProcessingStatus,
    val globalRiskScore: Int? = null,
    val detectedJurisdiction: String? = null,
    val rawText: String? = null,
    val errorLog: String? = null,
    val createdAt: String,
    val updatedAt: String,
    val deletedAt: String? = null
)
