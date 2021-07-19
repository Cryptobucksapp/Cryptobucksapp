package com.cryptobucksapp.cryptobucks.app.models.merchant_sale

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class InvoiceResponse(

    @Json(name = "invoice")
    val invoice: String? = null,

    @Json(name = "invoiceStatus")
    val invoiceStatus: String? = null,

    @Json(name = "cryptoAmount")
    val cryptoAmount: Double? = null,

    @Json(name = "cryptoFee")
    val cryptoFee: Double? = null,

    @Json(name = "fiatRate")
    val fiatRate: Double? = null,

    @Json(name = "fiatAmount")
    val fiatAmount: Double? = null,

    @Json(name = "crypto")
    val crypto: String? = null,

    @Json(name = "hash")
    val hash: String? = null,

    @Json(name = "data")
    val data: Data? = null
)