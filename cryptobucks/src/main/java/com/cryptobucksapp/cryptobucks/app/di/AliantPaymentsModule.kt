package com.cryptobucksapp.cryptobucks.app.di

import android.content.Context
import android.content.res.AssetManager
import android.content.res.Resources
import com.cryptobucksapp.cryptobucks.app.AliantPaymentsApp
import dagger.Module
import dagger.Provides

@Module
class AliantPaymentsModule(val app: AliantPaymentsApp) {
    @Provides
    @AliantPaymentsScope
    fun provideApp(): AliantPaymentsApp = app

    @Provides
    @AliantPaymentsScope
    fun provideResource(): Resources {
        return app.resources
    }

    @Provides
    @AliantPaymentsScope
    fun provideApplicationContext(): Context {
        return app
    }

    @Provides
    @AliantPaymentsScope
    fun provideAppComponent(appComponent: AliantPaymentsComponent): AliantPaymentsComponent {
        return appComponent
    }

    @Provides
    @AliantPaymentsScope
    fun provideAssets(): AssetManager {
        return app.assets
    }
}