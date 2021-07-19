package com.cryptobucksapp.cryptobucks.utils

import android.content.Context
import android.content.Intent
import com.cryptobucksapp.cryptobucks.ui.SDKActivity
import com.cryptobucksapp.cryptobucks.utils.manager.SessionManager

class cryptoBucksSdk(private val context : Context, private val key : String ) {

     fun launch() {
        SessionManager.getInstance().setToken(key)

        val intent = Intent(context, SDKActivity::class.java).apply {
        }
        context.startActivity(intent)
    }

}