package com.upitnik.playwithlessons.core.extensions

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.view.View

fun View.visible() = if (visibility != View.VISIBLE) visibility = View.VISIBLE else Unit

fun View.invisible() = if (visibility != View.INVISIBLE) visibility = View.INVISIBLE else Unit

fun View.gone() = if (visibility != View.GONE) visibility = View.GONE else Unit

fun View.shake() {
    val objAnim = ObjectAnimator.ofFloat(
        this,
        View.TRANSLATION_X,
        0F, 15F, -15F, 15F, -15F, 6F, -6F, 3F, -3F, 0F
    ).setDuration(750)
    objAnim.start()
}

fun View.shakeRotate() {
    val objAnim1 = ObjectAnimator.ofFloat(
        this,
        View.TRANSLATION_X,
        0F, 15F, -15F, 15F, -15F, 6F, -6F, 3F, -3F, 0F
    ).setDuration(750)
    val objAnim2 = ObjectAnimator.ofFloat(
        this,
        View.ROTATION,
        0F, 6F, -6F, 6F, -6F, 3F, -3F, 1F, -1F, 0F
    ).setDuration(750)

    val animatorSet = AnimatorSet()
    animatorSet.play(objAnim1).with(objAnim2)
    animatorSet.start()

}