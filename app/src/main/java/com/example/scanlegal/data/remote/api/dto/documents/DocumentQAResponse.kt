package com.example.scanlegal.data.remote.api.dto.documents

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class DocumentQAResponse(
    @Json(name = "answer")
    val answer: String,
    @Json(name = "citations")
    val citations: List<Map<String, Any>>
)
