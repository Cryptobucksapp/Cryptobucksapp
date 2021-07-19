package com.cryptobucksapp.cryptobucks.utils.manager

import com.cryptobucksapp.cryptobucks.R
import com.cryptobucksapp.cryptobucks.app.models.merchant_sale.InvoiceResponse
import com.cryptobucksapp.cryptobucks.utils.Commons
import com.cryptobucksapp.cryptobucks.utils.Constans

class SessionManager {

    private lateinit var response: InvoiceResponse

    companion object {
        private var INSTANCE: SessionManager? = null
        private const val SESSION_EXPIRE_TIME = "SessionExpireTime"
        private const val USER_HAS_LOGGED = "HasLoggedIn"
        var ENVIRONMENT = Commons.getString(R.string.base_url)

        fun getInstance(): SessionManager {
            if (INSTANCE == null)
                INSTANCE = SessionManager()

            return INSTANCE!!
        }
    }

    fun setToken(token: String?) {
        ENVIRONMENT = when {
            token!!.contains("key_dev") -> Commons.getString(R.string.base_url)
            token.contains("key_stg") -> Commons.getString(R.string.base_url_stg)
            else -> Commons.getString(R.string.base_url_release)
        }

        PreferenceManager.getInstance().putString(Constans.TOKEN, token)
    }

    fun getToken(): String? {
        return PreferenceManager.getInstance().getString(Constans.TOKEN, null)
    }


    fun setSessionExpireTime(value: Long) {
        PreferenceManager.getInstance().putLong(SESSION_EXPIRE_TIME, value)
    }

    fun getSessionExpireTime() = PreferenceManager.getInstance().getLong(SESSION_EXPIRE_TIME)

    fun resetSession() {
        setSessionExpireTime(CustomCookieManager.NO_EXPIRATION_SET)
    }

    fun setResponse(response: InvoiceResponse) {
        this.response = response
    }

    fun getResponse() = response
}