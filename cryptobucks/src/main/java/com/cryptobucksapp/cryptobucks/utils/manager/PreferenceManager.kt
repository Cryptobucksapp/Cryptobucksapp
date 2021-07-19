package com.cryptobucksapp.cryptobucks.utils.manager

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager.getDefaultSharedPreferences
import androidx.core.content.edit
import com.cryptobucksapp.cryptobucks.app.AliantPaymentsApp
import java.util.*

class PreferenceManager {
    val TAG = PreferenceManager::class.java.simpleName

    var myPrefs: SharedPreferences? = null

    var progressVerification: Boolean = false
    var device: Boolean = false
    var bankVerification: String = "0"
    var consult: String = ""
    var contactUs: String = ""
    var colorTint: Int = 0
    private var activePin: Boolean = true

    companion object {
        var pref: PreferenceManager? = null
        fun getInstance(): PreferenceManager {
            if (pref == null) {
                pref =
                    PreferenceManager(
                        AliantPaymentsApp.appContext
                    )
            }

            return pref!!
        }
    }

    init {
        val context = AliantPaymentsApp.appContext
        myPrefs = getDefaultSharedPreferences(context)
    }

    constructor(context: Context) {
        val context = context
        myPrefs = getDefaultSharedPreferences(context)
    }

    fun putString(key: String, value: String?): Boolean? {
        val editor = myPrefs?.edit()
        editor?.putString(key, value!!)
        return editor?.commit()
    }

    fun putStringSet(key: String, values: List<String>): Boolean? {
        val editor = myPrefs?.edit()
        editor?.putStringSet(key, HashSet(values))
        return editor?.commit()
    }

    fun getStringSet(key: String): MutableList<String> {
        val list = mutableListOf<String>()
        myPrefs?.let {
            val vals = it.getStringSet(key, null)
            if (vals != null)
                list.addAll(vals)
        }
        return list
    }

    fun getString(key: String, default_value: String?): String? {
        return myPrefs?.getString(key, default_value)
    }

    fun putInt(key: String, value: Int): Boolean? {
        val editor = myPrefs?.edit()
        editor?.putInt(key, value)
        return editor?.commit()
    }

    fun getInt(key: String, default_value: Int): Int {
        return try {
            Integer.parseInt(myPrefs!!.getString(key, default_value.toString() + "").toString())
        } catch (e: Exception) {
            myPrefs!!.getInt(key, default_value)
        }

    }

    fun putBoolean(key: String, value: Boolean?) {
        val editor = myPrefs?.edit()
        editor?.putBoolean(key, value!!)
        editor?.apply()
    }


    fun getBoolean(key: String, defValue: Boolean): Boolean {
        return myPrefs!!.getBoolean(key, defValue)
    }

    fun setDate(key: String, value: Date) {
        val time = value.time
        val editor = myPrefs?.edit()
        editor?.putLong(key, time)
        editor?.commit()
    }


    fun getDate(key: String): Date? {
        try {
            val value = myPrefs!!.getLong(key, -1)
            return if (!value.equals(-1)) {
                Date(value)
            } else
                null
        } catch (e: Exception) {
            return null
        }

    }

    fun putLong(key: String, value: Long) {
        myPrefs!!.edit { putLong(key, value) }
    }

    fun getLong(key: String) = myPrefs!!.getLong(key, CustomCookieManager.NO_EXPIRATION_SET)


    fun setProgress(progressVerification: Boolean) {
        this.progressVerification = progressVerification
    }

    fun getProgress() = progressVerification

    fun setVerification(bankVerification: String) {
        this.bankVerification = bankVerification
    }

    fun getVerification() = bankVerification

    fun setConsultFilter(consult: String) {
        this.consult = consult
    }

    fun getConsultFilter() = consult

    fun setColor(colorTint: Int) {
        this.colorTint = colorTint
    }

    fun getColor() = colorTint

    fun setActivePinStatus(activePin: Boolean) {
        this.activePin = activePin
    }

    fun getActivePinStatus() = activePin

    fun setContact(contactUs: String) {
        this.contactUs = contactUs
    }

    fun getContact() = contactUs

    fun setConnectionDevice(device: Boolean) {
        this.device = device
    }

    fun getConnectionDevice() = device
}