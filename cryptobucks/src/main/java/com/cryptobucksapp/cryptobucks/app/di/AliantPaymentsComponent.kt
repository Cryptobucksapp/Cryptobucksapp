package com.cryptobucksapp.cryptobucks.app.di

import com.cryptobucksapp.cryptobucks.app.network.api.InvoiceApi
import com.cryptobucksapp.cryptobucks.app.network.di.AliantPaymentsApiModule
import dagger.Component

@AliantPaymentsScope
@Component(modules = [AliantPaymentsModule::class, AliantPaymentsApiModule::class])
interface AliantPaymentsComponent {
    fun merchantApi() : InvoiceApi
}