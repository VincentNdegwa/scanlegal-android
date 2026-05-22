package com.example.scanlegal.data.local.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.scanlegal.domain.model.enums.MessageSender

@Entity(tableName = "chat_messages")
data class ChatMessageEntity(
    @PrimaryKey
    val id: String,
    val sessionId: String,
    val sender: MessageSender,
    val content: String,
    val createdAt: String
)
