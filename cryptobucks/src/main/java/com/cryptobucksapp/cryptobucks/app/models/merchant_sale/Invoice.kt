package com.cryptobucksapp.cryptobucks.app.models.merchant_sale

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Invoice(

    @Json(name = "amount")
    val amount: Int? = null,

    @Json(name = "networkFee")
    val networkFee: Double? = null,

    @Json(name = "webLink")
    val webLink: String? = null,

    @Json(name = "description")
    val description: String? = null,

    @Json(name = "tax")
    val tax: Int? = null,

    @Json(name = "type")
    val type: String? = null,

    @Json(name = "expireAt")
    val expireAt: String? = null,

    @Json(name = "env")
    val env: String? = null,

    @Json(name = "totalAmount")
    val totalAmount: Double? = null,

    @Json(name = "createdAt")
    val createdAt: String? = null,

    @Json(name = "phone")
    val phone: String? = null,

    @Json(name = "name")
    val name: String? = null,

    @Json(name = "physicalAddress")
    val physicalAddress: String? = null,

    @Json(name = "tip")
    val tip: Int? = null,

    @Json(name = "id")
    val id: String? = null,

    @Json(name = "email")
    val email: String? = null,

    @Json(name = "status")
    val status: String? = null,

    @Json(name = "updatedAt")
    val updatedAt: String? = null

)
