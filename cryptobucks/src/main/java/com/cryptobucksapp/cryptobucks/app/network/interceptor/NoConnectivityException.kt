package com.cryptobucksapp.cryptobucks.app.network.interceptor

import com.cryptobucksapp.cryptobucks.R
import com.cryptobucksapp.cryptobucks.utils.Commons
import java.io.IOException

class NoConnectivityException : IOException() {
    override val message: String?
        get() = Commons.getString(R.string.connectivity_exception)
}
