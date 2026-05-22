package com.example.scanlegal.data.remote.api.dto.auth

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class UserPreferencesResponse(
    @Json(name = "active_jurisdiction")
    val activeJurisdiction: String,
    @Json(name = "preferred_output_language")
    val preferredOutputLanguage: String = "en",
    @Json(name = "custom_playbook_rules")
    val customPlaybookRules: String? = null,
    @Json(name = "user_id")
    val userId: String,
    @Json(name = "onboarding_completed")
    val onboardingCompleted: Boolean,
    @Json(name = "updated_at")
    val updatedAt: String
)
