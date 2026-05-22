package com.example.scanlegal.data.remote.api.dto.auth

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class UserPreferencesUpdate(
    @Json(name = "active_jurisdiction")
    val activeJurisdiction: String? = null,
    @Json(name = "preferred_output_language")
    val preferredOutputLanguage: String? = null,
    @Json(name = "custom_playbook_rules")
    val customPlaybookRules: String? = null,
    @Json(name = "onboarding_completed")
    val onboardingCompleted: Boolean? = null
)
