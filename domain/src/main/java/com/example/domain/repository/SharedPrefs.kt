package com.example.domain.repository

import android.content.SharedPreferences

interface SharedPrefs {
    fun getSharedPrefs(): SharedPreferences
}