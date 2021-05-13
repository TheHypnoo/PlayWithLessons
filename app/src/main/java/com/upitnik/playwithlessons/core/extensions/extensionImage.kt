package com.upitnik.playwithlessons.core.extensions

import android.widget.ImageView
import com.bumptech.glide.Glide

fun ImageView.load(url: String) {
    if (url.isNotEmpty()) {
        Glide.with(this.context).load(url).into(this)
    }
}