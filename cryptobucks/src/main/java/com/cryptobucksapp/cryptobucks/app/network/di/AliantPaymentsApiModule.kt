package com.cryptobucksapp.cryptobucks.app.network.di

import com.cryptobucksapp.cryptobucks.app.di.AliantPaymentsScope
import com.cryptobucksapp.cryptobucks.app.network.api.InvoiceApi
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit

@Module(includes = [RetrofitModule::class])
class AliantPaymentsApiModule {

    @Provides
    @AliantPaymentsScope
    fun providerMerchantApi(retrofit: Retrofit) : InvoiceApi = retrofit.create(InvoiceApi::class.java)

}