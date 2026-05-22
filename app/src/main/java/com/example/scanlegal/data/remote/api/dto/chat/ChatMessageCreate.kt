package com.example.scanlegal.data.remote.api.dto.chat

import com.example.scanlegal.domain.model.enums.MessageSender
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ChatMessageCreate(
    @Json(name = "content")
    val content: String,
    @Json(name = "sender")
    val sender: MessageSender = MessageSender.USER
)
