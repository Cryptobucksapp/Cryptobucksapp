package com.cryptobucksapp.cryptobucks.ui.manager

import com.cryptobucksapp.cryptobucks.app.models.merchant_sale.InvoiceRequest

class Manager(private var apiKey: String, private var callback: Callback) {

    fun createInvoice(invoiceRequest: InvoiceRequest) {
        callback.onCreateInvoice(invoiceRequest)
    }
}