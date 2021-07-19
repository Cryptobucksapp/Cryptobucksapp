package com.cryptobucksapp.cryptobucks.app.models.address

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class AddressCryptoItem(

    @Json(name = "qr")
    val qr: String? = null,

    @Json(name = "qrData")
    val qrData: String? = null,

    @Json(name = "address")
    val address: String? = null
)