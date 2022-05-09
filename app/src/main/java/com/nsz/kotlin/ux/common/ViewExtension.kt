package com.nsz.kotlin.ux.common

import android.view.View
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume

private const val TAG = "ViewExtension"

suspend fun View.awaitNextLayout() = suspendCancellableCoroutine<Unit> { block ->
    val listener = object : View.OnLayoutChangeListener {
        override fun onLayoutChange(view: View, left: Int, top: Int, right: Int, bottom: Int, oldLeft: Int, oldTop: Int, oldRight: Int, oldBottom: Int) {
            view.removeOnLayoutChangeListener(this)
            block.resume(Unit)
        }
    }
    block.invokeOnCancellation { removeOnLayoutChangeListener(listener) }
    addOnLayoutChangeListener(listener)
}