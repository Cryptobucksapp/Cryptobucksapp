package com.cryptobucksapp.cryptobucks.ui.mvvm

import com.cryptobucksapp.cryptobucks.app.models.address.AddressCryptoResponse
import com.cryptobucksapp.cryptobucks.app.models.merchant_sale.InvoiceRequest
import com.cryptobucksapp.cryptobucks.app.models.merchant_sale.InvoiceResponse
import com.cryptobucksapp.cryptobucks.app.network.api.InvoiceApi
import com.cryptobucksapp.cryptobucks.utils.base.BaseRemoteRepository
import com.cryptobucksapp.cryptobucks.utils.base.RemoteErrorEmitter

class InvoiceRemoteRepository(private var invoiceApi: InvoiceApi) : BaseRemoteRepository(),
    InvoiceRepository {

    override suspend fun invoice(
        errorEmitter: RemoteErrorEmitter,
        invoiceRequest: InvoiceRequest
    ): InvoiceResponse? {
        return safeApiCall(errorEmitter) { invoiceApi.invoice(invoiceRequest) }
    }

    override suspend fun addressCrypto(
        errorEmitter: RemoteErrorEmitter,
        id: String,
        crypto: String
    ): AddressCryptoResponse? {
        return safeApiCall(errorEmitter) { invoiceApi.addressCrypto(id, crypto) }
    }
}