package com.example.data.repository

import android.content.Context
import android.content.SharedPreferences
import com.example.domain.repository.SharedPrefs
import javax.inject.Inject

class SharedPrefsImpl @Inject constructor(val context: Context): SharedPrefs {
    override fun getSharedPrefs(): SharedPreferences {
        return context.getSharedPreferences(context.packageName + "_preferences", Context.MODE_PRIVATE)
    }
}