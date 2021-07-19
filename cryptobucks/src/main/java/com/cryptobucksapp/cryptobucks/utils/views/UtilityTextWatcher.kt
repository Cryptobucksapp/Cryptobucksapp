package com.cryptobucksapp.cryptobucks.utils.views

import android.text.Editable
import android.text.TextWatcher

class UtilityTextWatcher(private val doAfterTextChanged:(s: Editable) -> Unit  ) : TextWatcher {

    override fun afterTextChanged(s: Editable) {
        doAfterTextChanged(s)
    }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
    }

}