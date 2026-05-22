package com.example.scanlegal.data.remote.api.dto.documents

import com.example.scanlegal.domain.model.enums.RiskSeverity
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class DocumentAnalysisFlagResponse(
    @Json(name = "id")
    val id: String,
    @Json(name = "document_id")
    val documentId: String,
    @Json(name = "clause_title")
    val clauseTitle: String,
    @Json(name = "risk_level")
    val riskLevel: RiskSeverity,
    @Json(name = "original_text")
    val originalText: String,
    @Json(name = "plain_english_translation")
    val plainEnglishTranslation: String,
    @Json(name = "counter_proposal")
    val counterProposal: String? = null,
    @Json(name = "suggested_script")
    val suggestedScript: String? = null,
    @Json(name = "is_jurisdiction_issue")
    val isJurisdictionIssue: Boolean = false,
    @Json(name = "created_at")
    val createdAt: String
)
