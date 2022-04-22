package com.example.mub2quizapp.activities.extensions

import android.app.Activity
import android.view.View
import android.view.inputmethod.InputMethodManager

fun View.hideKeyboard() {
    getInputMethodManager().hideSoftInputFromWindow(windowToken, 0)
}

private fun View.getInputMethodManager(): InputMethodManager {
    return context.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
}