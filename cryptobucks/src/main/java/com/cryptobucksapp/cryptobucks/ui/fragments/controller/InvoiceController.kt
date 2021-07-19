package com.cryptobucksapp.cryptobucks.ui.fragments.controller

import com.airbnb.epoxy.EpoxyController
import com.cryptobucksapp.cryptobucks.app.models.merchant_sale.CurrenciesItem
import com.cryptobucksapp.cryptobucks.crypto

class InvoiceController(
    private var listCrypto: List<CurrenciesItem?>?,
    private val invoice: String?,
    private val listener: CallbacksListener
) : EpoxyController() {

    override fun buildModels() {
        if (listCrypto == null) return

        listCrypto!!.forEach {
            crypto {
                id(it!!.id)
                item(it)
                clickListener { _, _, _, _ ->
                    listener.onClickItem(it, invoice)
                }
            }
        }
    }

    interface CallbacksListener {
        fun onClickItem(currenciesItem: CurrenciesItem, invoice: String?)
    }
}