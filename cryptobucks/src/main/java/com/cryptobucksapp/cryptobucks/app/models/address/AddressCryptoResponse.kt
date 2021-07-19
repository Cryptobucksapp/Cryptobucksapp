package com.cryptobucksapp.cryptobucks.app.models.address

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class AddressCryptoResponse(

    @Json(name = "data")
    val data: AddressCryptoItem? = null
)