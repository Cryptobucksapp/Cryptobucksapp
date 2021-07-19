package com.cryptobucksapp.cryptobucks.app.models.merchant_sale

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CurrenciesItem(

    @Json(name = "enable")
    val enable: Boolean? = null,

    @Json(name = "decimals")
    val decimals: Int? = null,

    @Json(name = "imageEnable")
    val imageEnable: String? = null,

    @Json(name = "name")
    val name: String? = null,

    @Json(name = "show")
    val show: Boolean? = null,

    @Json(name = "currency")
    val currency: String? = null,

    @Json(name = "id")
    val id: String? = null,

    @Json(name = "imageDisable")
    val imageDisable: String? = null,

    @Json(name = "multipleOutputs")
    val multipleOutputs: Boolean? = null,

    @Json(name = "env")
    val env: String? = null,

    @Json(name = "order")
    val order: Int? = null
)