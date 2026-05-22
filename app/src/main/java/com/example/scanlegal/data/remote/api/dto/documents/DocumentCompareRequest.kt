package com.example.scanlegal.data.remote.api.dto.documents

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class DocumentCompareRequest(
    @Json(name = "document_id_a")
    val documentIdA: String,
    @Json(name = "document_id_b")
    val documentIdB: String
)
