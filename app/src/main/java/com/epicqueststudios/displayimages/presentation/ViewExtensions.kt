package com.epicqueststudios.displayimages.presentation


import android.view.View

fun View.visible() {
    visibility = View.VISIBLE
}
fun View.invisible() {
    visibility = View.INVISIBLE
}
fun View.gone() {
    visibility = View.GONE
}

/** Convenient method that chooses between View.visible() or View.invisible() methods */
fun View.show(show: Boolean) {
    if (show) visible() else invisible()
}

/** Convenient method that chooses between View.visible() or View.gone() methods */
fun View.visibleOrGone(show: Boolean) {
    if (show) visible() else gone()
}
