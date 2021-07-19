package com.cryptobucksapp.cryptobucks.app.network.api

import com.cryptobucksapp.cryptobucks.app.models.address.AddressCryptoResponse
import com.cryptobucksapp.cryptobucks.app.models.merchant_sale.InvoiceRequest
import com.cryptobucksapp.cryptobucks.app.models.merchant_sale.InvoiceResponse
import retrofit2.http.*

interface InvoiceApi {

    @POST("/sdk/v1/new-invoice")
    suspend fun invoice(@Body invoiceRequest: InvoiceRequest): InvoiceResponse

    @GET("/web/v1/invoice/get-address")
    suspend fun addressCrypto(
        @Query("invoice") id: String,
        @Query("crypto") crypto: String
    ): AddressCryptoResponse

}