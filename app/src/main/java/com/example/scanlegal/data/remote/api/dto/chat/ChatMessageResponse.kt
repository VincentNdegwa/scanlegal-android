package com.example.scanlegal.data.remote.api.dto.chat

import com.example.scanlegal.domain.model.enums.MessageSender
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ChatMessageResponse(
    @Json(name = "id")
    val id: String,
    @Json(name = "session_id")
    val sessionId: String,
    @Json(name = "sender")
    val sender: MessageSender,
    @Json(name = "content")
    val content: String,
    @Json(name = "created_at")
    val createdAt: String
)
