package com.cryptobucksapp.cryptobucks.ui.manager

import com.cryptobucksapp.cryptobucks.app.models.merchant_sale.InvoiceRequest

interface Callback {

    fun onCreateInvoice(objects: InvoiceRequest)
    fun onPaymentReceived()
    fun onInvoiceExpired()
}