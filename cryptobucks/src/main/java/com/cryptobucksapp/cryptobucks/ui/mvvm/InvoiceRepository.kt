package com.cryptobucksapp.cryptobucks.ui.mvvm

import com.cryptobucksapp.cryptobucks.app.models.address.AddressCryptoResponse
import com.cryptobucksapp.cryptobucks.app.models.merchant_sale.InvoiceRequest
import com.cryptobucksapp.cryptobucks.app.models.merchant_sale.InvoiceResponse
import com.cryptobucksapp.cryptobucks.utils.base.RemoteErrorEmitter

interface InvoiceRepository {

    suspend fun invoice(
        errorEmitter: RemoteErrorEmitter,
        invoiceRequest: InvoiceRequest
    ): InvoiceResponse?

    suspend fun addressCrypto(errorEmitter: RemoteErrorEmitter, id: String, crypto: String) : AddressCryptoResponse?
}