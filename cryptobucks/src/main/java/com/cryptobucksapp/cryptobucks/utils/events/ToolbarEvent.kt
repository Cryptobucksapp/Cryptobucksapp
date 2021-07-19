package com.cryptobucksapp.cryptobucks.utils.events

import com.cryptobucksapp.cryptobucks.utils.views.AliantPaymentToolbar


data class ToolbarEvent(
    val showTitle: Boolean = true,
    val showArrow: Boolean = true,
    val showToolbar: Int = AliantPaymentToolbar.SHOW_TOOLBAR_BLUE,
    val title: String? = null,
    val iconToShow: Int = AliantPaymentToolbar.NO_ICON,
    val navigationClickCallback: (() -> Unit)? = null,
    val menuItemClickCallback: Boolean = true
)
