package com.cryptobucksapp.cryptobucks.utils.manager

import android.content.Context
import android.content.SharedPreferences
import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor


class CustomCookieManager: SharedPrefsCookiePersistor {

    companion object {
        const val SESSION_COOKIE_NAME = "appSession"
        const val NO_EXPIRATION_SET = 0L

        private lateinit var cookieInstance: CustomCookieManager

        fun getInstance(): CustomCookieManager = cookieInstance
    }

    init {
        cookieInstance = this
    }

    constructor(context: Context):super(context)

    constructor(sharedPrefs: SharedPreferences):super(sharedPrefs)

    fun retrieveSessionExpireTime(): Long {
        val cookies = loadAll()
        if (cookies.isEmpty()) return NO_EXPIRATION_SET
        return cookies
            .first { it.name == SESSION_COOKIE_NAME }
            .expiresAt
    }
}