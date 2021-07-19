package com.cryptobucksapp.cryptobucks.app.di


import com.cryptobucksapp.cryptobucks.app.network.di.RepositoriesModule
import com.cryptobucksapp.cryptobucks.ui.mvvm.MainActivityViewModel
import dagger.Component

@ViewModelScope
@Component(dependencies = [AliantPaymentsComponent::class], modules = [RepositoriesModule::class])
interface ViewModelInjector {

    fun inject(mainActivityViewModel: MainActivityViewModel)


    @Component.Builder
    interface Builder {
        fun build(): ViewModelInjector
        fun appComponent(appComponent: AliantPaymentsComponent): Builder
    }
}