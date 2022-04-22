package com.example.mub2quizapp.activities.extensions

import android.widget.TextView
import androidx.annotation.ColorRes
import androidx.core.content.res.ResourcesCompat

fun TextView.setTextColorCompat(@ColorRes colorId: Int) {
    setTextColor(ResourcesCompat.getColor(resources, colorId, null))
}