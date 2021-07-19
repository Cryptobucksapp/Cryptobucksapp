package com.cryptobucksapp.cryptobucks.utils.base

import android.os.Bundle
import android.os.PersistableBundle
import android.view.View
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.navigation.navOptions
import com.cryptobucksapp.cryptobucks.R
import com.cryptobucksapp.cryptobucks.utils.Commons
import org.jetbrains.anko.design.longSnackbar
import org.jetbrains.anko.design.snackbar

abstract class BaseActivity : AppCompatActivity {

    constructor() : super()
    constructor(@LayoutRes layout: Int) : super(layout)

    private val unknownErrorMessage = Commons.getString(R.string.unknown_error)
    private val timeoutErrorMessage = Commons.getString(R.string.try_again)
    private val noInternetError = Commons.getString(R.string.no_internet)

    private val mainView: View by lazy {
        findViewById<View>(android.R.id.content)
    }

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
    }

    fun getDefaultNavOptions(fade: Boolean = false) = navOptions {
        if (!fade) {
            anim {
                enter = R.anim.slide_in_right
                exit = R.anim.slide_out_left
                popExit = R.anim.slide_out_right
                popEnter = R.anim.slide_in_left
            }
        } else {
            anim {
                enter = R.anim.fade_in
                exit = R.anim.fade_out
                popExit = R.anim.fade_out
                popEnter = R.anim.fade_in
            }
        }
    }

    fun showSnackError(msg: String, long: Boolean = false) {
        val snack = if (!long) mainView.snackbar(msg)
        else mainView.longSnackbar(msg)
        snack.view.setBackgroundColor(ContextCompat.getColor(this, R.color.colorRed))
    }

    fun showErrorMessage(errorType: ErrorType) {
        when (errorType) {
            ErrorType.NETWORK -> showSnackError(noInternetError)
            ErrorType.TIMEOUT -> showSnackError(timeoutErrorMessage)
            else -> showSnackError(unknownErrorMessage)
        }
    }
}