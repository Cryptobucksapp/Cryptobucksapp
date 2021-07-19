package com.cryptobucksapp.cryptobucks.utils.views

import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import android.content.Context
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.cryptobucksapp.cryptobucks.R
import com.cryptobucksapp.cryptobucks.utils.Commons
import com.cryptobucksapp.cryptobucks.utils.base.BaseActivity
import com.cryptobucksapp.cryptobucks.utils.events.ToolbarEvent
import com.cryptobucksapp.cryptobucks.utils.manager.PreferenceManager
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.appbar.MaterialToolbar

class AliantPaymentToolbar @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : AppBarLayout(context, attrs, defStyleAttr) {

    companion object {
        const val NO_ICON = 0
        const val SHOW_ICON_CLOSE = 1
        const val SHOW_ICON = 2
        const val SHOW_FILTER = 3

        const val SHOW_TOOLBAR_BLUE = 4
        const val SHOW_TOOLBAR_GRADIENT = 5
        const val SHOW_TOOLBAR_BLACK = 6

        lateinit var statusListener: StatusListener
    }

    private val innerToolbar: MaterialToolbar
    private val toolbarTitle: TextView
    private val backButtonDrawable: Drawable?

    init {
        context.inflateCustomView(R.layout.aliant_payment_toolbar, this)
        innerToolbar = rootView.findViewById(R.id.inner_toolbar)
        toolbarTitle = rootView.findViewById(R.id.toolbar_title)
        backButtonDrawable = ContextCompat.getDrawable(context, R.drawable.ic_back_arrow)
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    fun onToolbarEvent(toolbarEvent: ToolbarEvent) {
        with(toolbarEvent) {
            android.util.Log.d(TAG, "Toolbar event: $toolbarEvent")
            setIconToShow(iconToShow, menuItemClickCallback)

            when (showToolbar) {
                SHOW_TOOLBAR_BLUE -> innerToolbar.background =
                    ColorDrawable(resources.getColor(R.color.colorBlueToolbar))
                SHOW_TOOLBAR_GRADIENT -> innerToolbar.background =
                    resources.getDrawable(R.drawable.gradient_toolbar, null)
                SHOW_TOOLBAR_BLACK -> innerToolbar.background =
                    ColorDrawable(resources.getColor(R.color.colorBlackHome))
            }

            if (!showArrow) {
                innerToolbar.navigationIcon = null
                toolbarTitle.text = title ?: throw NullPointerException("You must set a title")
                return
            }

            if (!showTitle) {
                innerToolbar.navigationIcon = null
                toolbarTitle.text = ""
                return
            }

            toolbarTitle.text = title ?: throw NullPointerException("You must set a title")
            innerToolbar.navigationIcon = backButtonDrawable

            innerToolbar.setNavigationOnClickListener { navigationClickCallback?.invoke() }
        }
    }

    private fun setIconToShow(iconToShow: Int, menuItemClickCallback: Boolean) {
        require(!(iconToShow < NO_ICON || iconToShow > SHOW_TOOLBAR_BLACK)) { "Provide a proper icon value" }

        val overflowIcon = innerToolbar.overflowIcon

        if (overflowIcon != null) {
            when (iconToShow) {
                SHOW_ICON -> innerToolbar.overflowIcon = Commons.getDrawable(R.drawable.icn_options)
                SHOW_FILTER -> changeInnerToolbar(PreferenceManager.getInstance().getColor())
            }
        }

        val menu = innerToolbar.menu
        val menuItemLogout = menu.findItem(R.id.logout_item)
        val menuItemSupport = menu.findItem(R.id.support_item)
        val menuItemAbout = menu.findItem(R.id.about_item)
        val menuItemClose = menu.findItem(R.id.close_item)

        when (iconToShow) {
            NO_ICON -> {
                menuItemLogout.isVisible = false
                menuItemSupport.isVisible = false
                menuItemAbout.isVisible = false
                menuItemClose.isVisible = false
            }
            SHOW_ICON_CLOSE -> {
                menuItemClose.isVisible = true
                menuItemLogout.isVisible = false
                menuItemSupport.isVisible = false
                menuItemAbout.isVisible = false
                menuItemClose.setOnMenuItemClickListener { close(menuItemClickCallback) }
            }
            SHOW_ICON -> {
                menuItemLogout.isVisible = true
                menuItemSupport.isVisible = false
                menuItemAbout.isVisible = false
                menuItemClose.isVisible = false
                menuItemLogout.setOnMenuItemClickListener { logout(menuItemClickCallback) }
                menuItemSupport.setOnMenuItemClickListener { support(menuItemClickCallback) }
                menuItemAbout.setOnMenuItemClickListener { about(menuItemClickCallback) }
            }
            SHOW_FILTER -> {
                menuItemLogout.isVisible = false
                menuItemSupport.isVisible = false
                menuItemAbout.isVisible = false
                menuItemClose.isVisible = false
            }
        }
    }

    private fun logout(menuItemClickCallback: Boolean): Boolean {
        return menuItemClickCallback
    }

    private fun support(menuItemClickCallback: Boolean): Boolean {
        return menuItemClickCallback
    }

    private fun about(menuItemClickCallback: Boolean): Boolean {
        return menuItemClickCallback
    }

    private fun close(menuItemClickCallback: Boolean): Boolean {
        return menuItemClickCallback
    }

    private fun changeInnerToolbar(color: Int) {
        PreferenceManager.getInstance().setColor(color)
        innerToolbar.overflowIcon =
            Commons.getColorTint(R.drawable.ic_icn_filter, color)
    }

    interface StatusListener {
        fun filterStatus(status: String)
    }
}