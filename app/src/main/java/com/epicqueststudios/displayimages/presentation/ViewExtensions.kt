package com.epicqueststudios.displayimages.presentation


import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
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

fun isOnline(context: Context): Boolean {
    val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val network = connectivityManager.activeNetwork ?: return false
    val capabilities = connectivityManager.getNetworkCapabilities(network) ?: return false
    return capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_VALIDATED)
}