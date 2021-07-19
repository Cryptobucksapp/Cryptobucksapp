package com.cryptobucksapp.cryptobucks.app

import android.app.Application
import android.content.Context
import com.cryptobucksapp.cryptobucks.app.di.*


class AliantPaymentsApp : Application() {

    val appComponent: AliantPaymentsComponent by lazy {
        initComponent()
    }

    private val viewModelInjector: ViewModelInjector by lazy {
        DaggerViewModelInjector
            .builder()
            .appComponent(appComponent)
            .build()
    }


    companion object {
        lateinit var appInstance: AliantPaymentsApp
        val appContext: Context by lazy {
            appInstance.applicationContext
        }
    }

    override fun onCreate() {
        super.onCreate()
        appInstance = this
        initComponent()
    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
    }

    private fun initComponent(): AliantPaymentsComponent =
        DaggerAliantPaymentsComponent
        .builder()
            .aliantPaymentsModule(AliantPaymentsModule(appInstance))
            .build()

    fun getInjector(): ViewModelInjector = viewModelInjector
}