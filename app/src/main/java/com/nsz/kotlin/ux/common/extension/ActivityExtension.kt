package com.nsz.kotlin.ux.common.extension

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.View

fun <V : View> Activity.bindView(id: Int): Lazy<V> = lazy {
    viewFinder(id) as V
}

val Activity.viewFinder: Activity.(Int) -> View
    get() = { findViewById(it) }

// val textView by bindView<TextView>(R.id.text_view)
// textView.text="执行到我时。才会进行控件初始化"

inline fun <reified T : Activity> Activity.startActivity() {
    val intent = Intent(this, T::class.java)
    startActivity(intent)
}

inline fun <reified T : Activity> Activity.startActivity(block: Intent.() -> Unit) {
    val intent = Intent(this, T::class.java)
    intent.block()
    startActivity(intent)
}

inline fun <reified T : Activity> startActivity(context: Context) {
    val intent = Intent(context, T::class.java)
    context.startActivity(intent)
}

inline fun <reified T : Activity> startActivity(context: Context, block: Intent.() -> Unit) {
    val intent = Intent(context, T::class.java)
    intent.block()
    context.startActivity(intent)
}