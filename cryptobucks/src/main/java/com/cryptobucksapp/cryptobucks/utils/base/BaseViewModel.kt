package com.cryptobucksapp.cryptobucks.utils.base

import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.cryptobucksapp.cryptobucks.app.AliantPaymentsApp
import com.cryptobucksapp.cryptobucks.ui.mvvm.MainActivityViewModel
import com.cryptobucksapp.cryptobucks.utils.Commons
import com.cryptobucksapp.cryptobucks.utils.events.EventLiveData
import retrofit2.HttpException
import java.io.IOException
import java.net.SocketTimeoutException

abstract class BaseViewModel : ViewModel(), RemoteErrorEmitter {

    val mutableProgress = MutableLiveData<Int>(View.GONE)
    val mutableVisibility = MutableLiveData<Int>(View.INVISIBLE)
    val mutableVisibilityPIN = MutableLiveData<Int>(View.GONE)
    val mutableErrorMessage = MutableLiveData<EventLiveData<String>>()
    val mutableNotifyMessage = MutableLiveData<EventLiveData<String>>()
    val mutableSuccessMessage = MutableLiveData<EventLiveData<String>>()
    val mutableErrorType = MutableLiveData<EventLiveData<ErrorType>>()

    private val injector = AliantPaymentsApp.appInstance.getInjector()

    init {
        inject()
    }

    private fun inject() {
        when (this) {
            is MainActivityViewModel -> injector.inject(this)
        }
    }

    fun getError(it: Throwable) {
        when (it) {
            is HttpException -> {
                if (it.code() == 401) mutableErrorType.value =
                    EventLiveData(ErrorType.SESSION_EXPIRED)
                else {
                    val body = it.response()?.errorBody()
                    mutableErrorMessage.value =
                        EventLiveData(Commons.getErrorMessage(body))
                }

            }
            is SocketTimeoutException -> mutableErrorType.value =
                EventLiveData(ErrorType.TIMEOUT)
            is IOException -> mutableErrorType.value =
                EventLiveData(ErrorType.NETWORK)
            else -> mutableErrorType.value =
                EventLiveData(ErrorType.UNKNOWN)
        }
    }

    override fun onError(errorType: ErrorType) {
        mutableErrorType.postValue(EventLiveData(errorType))
    }

    override fun onError(msg: String) {
        mutableErrorMessage.postValue(EventLiveData(msg))
    }
}