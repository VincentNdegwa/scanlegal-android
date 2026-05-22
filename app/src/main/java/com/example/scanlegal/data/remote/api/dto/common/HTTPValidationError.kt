package com.example.scanlegal.data.remote.api.dto.common

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class HTTPValidationError(
    @Json(name = "detail")
    val detail: List<ValidationError>
)
