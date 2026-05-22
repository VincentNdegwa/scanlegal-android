package com.example.scanlegal.data.remote.api.dto.common

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ValidationError(
    @Json(name = "loc")
    val loc: List<Any>,
    @Json(name = "msg")
    val msg: String,
    @Json(name = "type")
    val type: String
)
