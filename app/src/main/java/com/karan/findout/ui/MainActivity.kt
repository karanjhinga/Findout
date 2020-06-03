package com.karan.findout.ui

import android.os.Bundle
import androidx.navigation.findNavController
import com.karan.findout.R
import com.karan.findout.utils.hideKeyboard
import dagger.android.support.DaggerAppCompatActivity

class MainActivity : DaggerAppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.AppTheme)
        setContentView(R.layout.activity_main)

        findNavController(R.id.nav_host_fragment).addOnDestinationChangedListener { _, _, _ ->
            hideKeyboard()
        }
    }
}