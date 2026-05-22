package com.example.scanlegal.data.remote.api.dto.documents

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class DocumentDeadlineResponse(
    @Json(name = "id")
    val id: String,
    @Json(name = "document_id")
    val documentId: String,
    @Json(name = "description")
    val description: String,
    @Json(name = "deadline_date")
    val deadlineDate: String,
    @Json(name = "is_recurring")
    val isRecurring: Boolean = false,
    @Json(name = "notification_triggered")
    val notificationTriggered: Boolean,
    @Json(name = "created_at")
    val createdAt: String
)
