package com.example.scanlegal.data.remote.api.dto.chat

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ChatSessionResponse(
    @Json(name = "id")
    val id: String,
    @Json(name = "document_id")
    val documentId: String,
    @Json(name = "user_id")
    val userId: String,
    @Json(name = "created_at")
    val createdAt: String,
    @Json(name = "updated_at")
    val updatedAt: String
)
