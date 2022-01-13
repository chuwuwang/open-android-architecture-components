package com.nsz.kotlin.aac

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import androidx.viewbinding.ViewBinding
import java.lang.reflect.ParameterizedType

open class ViewBindingFragment<T : ViewBinding> : Fragment() {

    private var _binding: T ? = null
    protected val binding get() = _binding !!

    @Suppress("UNCHECKED_CAST", "unused")
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup ?, savedInstanceState: Bundle ? ): View {
        val simpleName = javaClass.simpleName
        val type = javaClass.genericSuperclass
        val clazz = (type as ParameterizedType).actualTypeArguments[0] as Class<T>
        val method = clazz.getMethod("inflate", LayoutInflater::class.java, ViewGroup::class.java, Boolean::class.java)
        _binding = method.invoke(null, layoutInflater, container, false) as T
        viewLifecycleOwner.lifecycle.addObserver(object : LifecycleObserver {

                @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
                fun autoDestroyView() {
                    Log.d(TAG, "$simpleName autoDestroyView method is called")
                    onViewDestroy()
                    _binding = null
                }

            }
        )
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
    }

    open fun onViewDestroy() {

    }

    companion object {
        private const val TAG = "ViewBindingFragment"
    }

}