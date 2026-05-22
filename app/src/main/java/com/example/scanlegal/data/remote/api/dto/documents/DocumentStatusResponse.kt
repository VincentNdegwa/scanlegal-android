package com.example.scanlegal.data.remote.api.dto.documents

import com.example.scanlegal.domain.model.enums.ProcessingStatus
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class DocumentStatusResponse(
    @Json(name = "status")
    val status: ProcessingStatus,
    @Json(name = "error")
    val error: String? = null
)
