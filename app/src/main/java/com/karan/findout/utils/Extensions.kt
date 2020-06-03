package com.karan.findout.utils

import android.app.Activity
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.core.content.getSystemService
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar

fun Activity.hideKeyboard() {
    if (currentFocus != null) {
        val inputMethodManager: InputMethodManager? = getSystemService()
        inputMethodManager?.hideSoftInputFromWindow(currentFocus!!.windowToken, 0)
    }
}

fun View.showSnackBar(message: String?) {
    if (message != null) {
        Snackbar.make(this, message, Snackbar.LENGTH_SHORT).show()
    }
}

fun Fragment.showSnackBar(message: String?) = view?.showSnackBar(message)