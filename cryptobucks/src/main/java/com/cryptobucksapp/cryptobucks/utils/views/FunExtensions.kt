package com.cryptobucksapp.cryptobucks.utils.views

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup

fun Context.inflateCustomView(layout: Int, viewGroup: ViewGroup?) {
    val inflater= this.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
    inflater.inflate(layout, viewGroup, true)
}