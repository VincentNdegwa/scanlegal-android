package com.example.scanlegal.data.remote.api.dto.auth

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ProfileWithPreferences(
    @Json(name = "email")
    val email: String,
    @Json(name = "id")
    val id: String,
    @Json(name = "is_active")
    val isActive: Boolean,
    @Json(name = "created_at")
    val createdAt: String,
    @Json(name = "updated_at")
    val updatedAt: String,
    @Json(name = "user_tier")
    val userTier: String = "FREE",
    @Json(name = "preferences")
    val preferences: UserPreferencesResponse? = null
)
