package com.cryptobucksapp.cryptobucks.app.models.merchant_sale

import com.cryptobucksapp.cryptobucks.app.models.merchant_sale.HistoryItem
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class MerchantSaleItem(

    @Json(name = "id")
    val uid: String? = null,

    @Json(name = "createdAt")
    val createdAt: String? = null,

    @Json(name = "amount")
    val amount: Double? = null,

    @Json(name = "tax")
    val tax: Double? = null,

    @Json(name = "tip")
    val tip: Double? = null,

    @Json(name = "phone")
    val phone: String? = null,

    @Json(name = "physicalAddress")
    val physicalAddress: String? = null,

    @Json(name = "type")
    val type: String? = null,

    @Json(name = "env")
    val env: String? = null,

    @Json(name = "history")
    val history: List<HistoryItem?>? = null,

    @Json(name = "name")
    val name: String? = null,

    @Json(name = "description")
    val description: String? = null,

    @Json(name = "status")
    val invoiceStatus: String? = null,

    @Json(name = "email")
    val email: String? = null,

    @Json(name = "expireAt")
    val expireAt: String? = null,

    @Json(name = "updatedAt")
    val updatedAt: String? = null
)