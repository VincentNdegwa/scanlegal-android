package com.example.scanlegal.data.remote.api.dto.billing

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class UsageResponse(
    @Json(name = "scans_used_this_month")
    val scansUsedThisMonth: Int,
    @Json(name = "current_billing_cycle_start")
    val currentBillingCycleStart: String,
    @Json(name = "current_balance")
    val currentBalance: Int
)
