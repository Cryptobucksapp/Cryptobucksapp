package com.cryptobucksapp.cryptobucks.utils.views

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.EditText
import android.widget.LinearLayout
import com.cryptobucksapp.cryptobucks.R
import kotlinx.android.synthetic.main.keyboard_layout.view.*

class KeyBoardPayView : LinearLayout {

    var tvAmount: EditText? = null

    constructor(context: Context) : super(context) {
        init(null, context)
    }

    constructor(
            context: Context,
            attrs: AttributeSet?
    ) : super(context, attrs) {
        init(attrs, context)
    }

    constructor(
            context: Context,
            attrs: AttributeSet?,
            defStyleAttr: Int
    ) : super(context, attrs, defStyleAttr) {
        init(attrs, context)
    }

    private fun init(
            attrs: AttributeSet?,
            context: Context
    ) {
        val inflater =
                context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        inflater.inflate(R.layout.keyboard_layout, this, true)

        btn_point.setOnClickListener {}

        btn_zero.setOnClickListener {
            tvAmount!!.append("0")
        }
        btn_one.setOnClickListener {
            tvAmount!!.append("1")
        }
        btn_two.setOnClickListener {
            tvAmount!!.append("2")
        }
        btn_three.setOnClickListener {
            tvAmount!!.append("3")
        }
        btn_four.setOnClickListener {
            tvAmount!!.append("4")
        }
        btn_five.setOnClickListener {
            tvAmount!!.append("5")
        }
        btn_six.setOnClickListener {
            tvAmount!!.append("6")
        }
        btn_seven.setOnClickListener {
            tvAmount!!.append("7")
        }
        btn_eight.setOnClickListener {
            tvAmount!!.append("8")
        }
        btn_nine.setOnClickListener {
            tvAmount!!.append("9")
        }
        btn_delete_number.setOnClickListener {
            var s = tvAmount!!.text.toString()
            if (s.length > 0) {
                s = s.substring(0, s.length - 1)
                tvAmount!!.setText(s)
            }
        }
    }

    fun setTextView(tvAmount: EditText) {
        this.tvAmount = tvAmount
        this.tvAmount!!.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(
                    charSequence: CharSequence,
                    i: Int,
                    i1: Int,
                    i2: Int
            ) {}
            override fun onTextChanged(
                    charSequence: CharSequence,
                    i: Int,
                    i1: Int,
                    i2: Int
            ) {
                if (!charSequence.toString()
                                .matches("^\\(\\d{0,3}(\\,\\d{3})*|(\\d+)(\\.\\d{2})?$".toRegex())
                ) {
                    val userInput =
                            "" + charSequence.toString().replace("[^\\d]".toRegex(), "")
                    val cashAmountBuilder =
                            StringBuilder(userInput)
                    while (cashAmountBuilder.length > 3 && cashAmountBuilder[0] == '0') {
                        cashAmountBuilder.deleteCharAt(0)
                    }
                    while (cashAmountBuilder.length < 3) {
                        cashAmountBuilder.insert(0, '0')
                    }
                    cashAmountBuilder.insert(cashAmountBuilder.length - 2, '.')
                    //  cashAmountBuilder.insert(0, ' ')
                    tvAmount.setText(cashAmountBuilder.toString())
                    // keeps the cursor always to the right
                    // Selection.setSelection(amountEditText.getText(), cashAmountBuilder.toString().length());
                }
            }
            override fun afterTextChanged(editable: Editable) {}
        })
    }

    fun setText(tvAmount: EditText) {
        this.tvAmount = tvAmount
        this.tvAmount!!.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(
                charSequence: CharSequence,
                i: Int,
                i1: Int,
                i2: Int
            ) {}
            override fun onTextChanged(
                charSequence: CharSequence,
                i: Int,
                i1: Int,
                i2: Int
            ) {
                if (!charSequence.toString()
                        .matches("^\\$(\\d{1,3}(\\,\\d{3})*|(\\d+))(\\.\\d{2})$?".toRegex())
                ) {
                    val userInput =
                        "" + charSequence.toString().replace("[^\\d]".toRegex(), "")
                    val cashAmountBuilder =
                        StringBuilder(userInput)
                    while (cashAmountBuilder.length > 3 && cashAmountBuilder[0] == '0') {
                        cashAmountBuilder.deleteCharAt(0)
                    }
                    while (cashAmountBuilder.length < 3) {
                        cashAmountBuilder.insert(0, '0')
                    }
                    cashAmountBuilder.insert(cashAmountBuilder.length - 2, '.')
                    cashAmountBuilder.insert(0, '$')
                    tvAmount.setText(cashAmountBuilder.toString())
                    // keeps the cursor always to the right
                    // Selection.setSelection(amountEditText.getText(), cashAmountBuilder.toString().length());
                }
            }
            override fun afterTextChanged(editable: Editable) {}
        })
    }
}