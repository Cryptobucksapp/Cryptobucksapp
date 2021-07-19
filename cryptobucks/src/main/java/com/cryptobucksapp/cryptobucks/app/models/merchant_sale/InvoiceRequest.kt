package com.cryptobucksapp.cryptobucks.app.models.merchant_sale

data class InvoiceRequest(
    var amount: Double? = null,
    var phone: String? = null,
    var name: String? = null,
    var description: String? = null,
    var email: String? = null
)
