package com.nsz.kotlin.ux.common

import java.util.concurrent.Executors

private val IO_EXECUTOR = Executors.newSingleThreadExecutor()

/**
 * Utility method to run blocks on a dedicated background thread, used for io/database work.
 */
fun ioThread(runnable: () -> Unit) {
    IO_EXECUTOR.execute(runnable)
}