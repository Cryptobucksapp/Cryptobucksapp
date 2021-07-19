package com.cryptobucksapp.cryptobucks.utils

import android.text.TextWatcher
import android.util.TypedValue
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.cryptobucksapp.cryptobucks.R
import com.cryptobucksapp.cryptobucks.app.AliantPaymentsApp

@BindingAdapter("mutableText")
fun setMutableText(view: TextView, text: MutableLiveData<String>?) {
    val parentActivity: AppCompatActivity? = view.getParentActivity()
    if (parentActivity != null && text != null)

        if (text.value != Commons.getString(R.string.text_failed_transaction))
            text.observe(parentActivity, Observer { value -> view.text = value ?: "" })
        else {
            view.setTextSize(
                TypedValue.COMPLEX_UNIT_PX,
                AliantPaymentsApp.appContext.resources.getDimension(R.dimen.result_font)
            )
            text.observe(parentActivity, Observer { value -> view.text = value ?: "" })
        }
}

@BindingAdapter("mutableVisibility")
fun setMutableVisibility(view: View, visibility: MutableLiveData<Int>) {
    val parentActivity: AppCompatActivity? = view.getParentActivity()
    if (parentActivity != null && view != null)
        visibility.observe(parentActivity, Observer { value ->
            view.visibility = value ?: View.GONE
        })
}

@BindingAdapter("mutableTextWatcher")
fun setMutableTextWatcher(edittext: EditText, textWatcher: TextWatcher) {
    val parentActivity: AppCompatActivity? = edittext.getParentActivity()
    if (parentActivity != null && edittext != null)
        edittext.addTextChangedListener(textWatcher)
}

@BindingAdapter("mutableLoadImage")
fun setMutableLoadImage(view: ImageView, url: String?) {
    val parentActivity: AppCompatActivity? = view.getParentActivity()
    if (parentActivity != null && view != null && url != null)
        Glide.with(view.context)
            .load(url)
            .override(300, 300)
            .placeholder(R.drawable.background_select_image)
            .error(R.drawable.ic_camera)
            .centerCrop()
            .into(view)
}

@BindingAdapter("mutableLoadImageCrypto")
fun setMutableLoadImageCrypto(view: ImageView, url: String?) {
    view.setColorFilter(
        ContextCompat.getColor(AliantPaymentsApp.appContext, R.color.colorWhite),
        android.graphics.PorterDuff.Mode.SRC_IN
    )
    val parentActivity: AppCompatActivity? = view.getParentActivity()
    if (parentActivity != null && view != null && url != null)
        Glide.with(view.context)
            .load(url)
            .override(300, 300)
            .placeholder(R.drawable.icn_lite)
            .error(R.drawable.icn_lite)
            .centerCrop()
            .into(view)

}

@BindingAdapter("mutableImage")
fun setMutableImage(view: ImageView, drawable: Int) {
    view.setImageResource(drawable)
}