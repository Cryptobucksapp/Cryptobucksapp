package com.cryptobucksapp.cryptobucks.app.models.merchant_sale

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class HistoryItem(

    @Json(name = "createdAt")
    val createdAt: String? = null,

    @Json(name = "id")
    val id: String? = null,

    @Json(name = "status")
    val status: String? = null,

    @Json(name = "updatedAt")
    val updatedAt: String? = null
)