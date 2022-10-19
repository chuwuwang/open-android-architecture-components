package com.nsz.kotlin.ux.common

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume

/**
 * 增加一个处理协程取消的监听器, 如果协程被取消, 同时执行动画监听器的 onAnimationCancel()方法, 取消动画
 */
suspend fun Animator.awaitEnd() = suspendCancellableCoroutine<Unit> { block ->
    block.invokeOnCancellation { cancel() }
    val listener = object : AnimatorListenerAdapter() {
        private var endedSuccessfully = true

        override fun onAnimationCancel(animation: Animator) {
            super.onAnimationCancel(animation)
            endedSuccessfully = false
        }

        override fun onAnimationEnd(animation: Animator) {
            super.onAnimationEnd(animation)
            if (animation != null) {
                animation.removeListener(this)
            } else {
                // animation is null
            }
            if (block.isActive) {
                if (endedSuccessfully) {
                    block.resume(Unit)
                } else {
                    block.cancel()
                }
            }
        }
    }
    addListener(listener)
}