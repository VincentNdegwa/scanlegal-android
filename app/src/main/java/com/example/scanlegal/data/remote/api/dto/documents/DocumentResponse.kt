package com.example.scanlegal.data.remote.api.dto.documents

import com.example.scanlegal.domain.model.enums.ProcessingStatus
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class DocumentResponse(
    @Json(name = "id")
    val id: String,
    @Json(name = "title")
    val title: String,
    @Json(name = "document_type")
    val documentType: String? = null,
    @Json(name = "page_count")
    val pageCount: Int,
    @Json(name = "user_id")
    val userId: String,
    @Json(name = "status")
    val status: ProcessingStatus,
    @Json(name = "global_risk_score")
    val globalRiskScore: Int? = null,
    @Json(name = "detected_jurisdiction")
    val detectedJurisdiction: String? = null,
    @Json(name = "created_at")
    val createdAt: String,
    @Json(name = "updated_at")
    val updatedAt: String
)
