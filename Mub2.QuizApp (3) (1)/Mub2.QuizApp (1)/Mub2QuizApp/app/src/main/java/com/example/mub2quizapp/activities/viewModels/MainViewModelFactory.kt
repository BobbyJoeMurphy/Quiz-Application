package com.example.mub2quizapp.activities.viewModels

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.mub2quizapp.activities.model.SQLiteHelper

class MainViewModelFactory(val context: Context) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel(SQLiteHelper(context)) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}