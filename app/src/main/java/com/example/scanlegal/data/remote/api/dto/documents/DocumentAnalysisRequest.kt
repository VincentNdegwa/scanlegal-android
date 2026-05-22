package com.example.scanlegal.data.remote.api.dto.documents

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class DocumentAnalysisRequest(
    @Json(name = "title")
    val title: String,
    @Json(name = "page_count")
    val pageCount: Int,
    @Json(name = "file_hash")
    val fileHash: String,
    @Json(name = "raw_text")
    val rawText: String? = null,
    @Json(name = "storage_key")
    val storageKey: String? = null,
    @Json(name = "document_type")
    val documentType: String? = null
)
