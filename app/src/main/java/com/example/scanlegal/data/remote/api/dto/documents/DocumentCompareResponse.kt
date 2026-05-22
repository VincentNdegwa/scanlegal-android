package com.example.scanlegal.data.remote.api.dto.documents

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class DocumentCompareResponse(
    @Json(name = "summary")
    val summary: String,
    @Json(name = "changes")
    val changes: List<Map<String, Any>>,
    @Json(name = "assessment")
    val assessment: String
)
