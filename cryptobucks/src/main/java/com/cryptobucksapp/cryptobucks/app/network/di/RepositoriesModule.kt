package com.cryptobucksapp.cryptobucks.app.network.di

import com.cryptobucksapp.cryptobucks.app.network.api.InvoiceApi
import com.cryptobucksapp.cryptobucks.ui.mvvm.InvoiceRemoteRepository
import com.cryptobucksapp.cryptobucks.ui.mvvm.InvoiceRepository
import dagger.Module
import dagger.Provides

@Module
class RepositoriesModule {

    @Provides
    fun provideMerchant(invoiceApi: InvoiceApi): InvoiceRepository =
        InvoiceRemoteRepository(
            invoiceApi
        )
}