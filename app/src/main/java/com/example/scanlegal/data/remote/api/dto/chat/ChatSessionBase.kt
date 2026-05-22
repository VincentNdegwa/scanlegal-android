package com.example.scanlegal.data.remote.api.dto.chat

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ChatSessionBase(
    @Json(name = "document_id")
    val documentId: String
)
