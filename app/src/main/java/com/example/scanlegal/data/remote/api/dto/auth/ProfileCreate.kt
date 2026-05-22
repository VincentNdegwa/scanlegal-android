package com.example.scanlegal.data.remote.api.dto.auth

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ProfileCreate(
    @Json(name = "email")
    val email: String,
    @Json(name = "id")
    val id: String,
    @Json(name = "display_name")
    val displayName: String? = null,
    @Json(name = "user_tier")
    val userTier: String = "FREE"
)
