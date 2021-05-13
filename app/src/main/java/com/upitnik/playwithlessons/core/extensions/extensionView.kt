package com.upitnik.playwithlessons.core.extensions

import android.view.View

fun View.visible() = if (visibility != View.VISIBLE) visibility = View.VISIBLE else Unit

fun View.invisible() = if (visibility != View.INVISIBLE) visibility = View.INVISIBLE else Unit

fun View.gone() = if (visibility != View.GONE) visibility = View.GONE else Unit