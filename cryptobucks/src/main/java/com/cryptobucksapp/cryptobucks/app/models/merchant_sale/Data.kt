package com.cryptobucksapp.cryptobucks.app.models.merchant_sale


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import java.util.*

@JsonClass(generateAdapter = true)
data class Data(

@Json(name = "invoice")
val invoice: Invoice? = null,

@Json(name="currencies")
val currencies: List<CurrenciesItem?>? = null



)