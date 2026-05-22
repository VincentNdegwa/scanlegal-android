package com.example.scanlegal.data.local.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.scanlegal.domain.model.enums.RiskSeverity

@Entity(tableName = "document_analysis_flags")
data class DocumentAnalysisFlagEntity(
    @PrimaryKey
    val id: String,
    val documentId: String,
    val clauseTitle: String,
    val riskLevel: RiskSeverity,
    val originalText: String,
    val plainEnglishTranslation: String,
    val counterProposal: String? = null,
    val suggestedScript: String? = null,
    val isJurisdictionIssue: Boolean = false,
    val createdAt: String
)
