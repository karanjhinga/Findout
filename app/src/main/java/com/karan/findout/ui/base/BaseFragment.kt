package com.karan.findout.ui.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewbinding.ViewBinding
import dagger.android.support.DaggerFragment

abstract class BaseFragment<T : ViewBinding> : DaggerFragment() {

    var binding: T? = null
        private set

    abstract fun initBinding(inflater: LayoutInflater, container: ViewGroup?): T

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = initBinding(inflater, container)
        return binding?.root
    }

    inline fun requireBinding(bind: T.() -> Unit) = binding?.let(bind)

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}
