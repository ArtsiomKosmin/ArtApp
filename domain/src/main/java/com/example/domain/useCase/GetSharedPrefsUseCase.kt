package com.example.domain.useCase

import android.content.SharedPreferences
import com.example.domain.repository.SharedPrefs

class GetSharedPrefsUseCase(
    private val sharedPrefs: SharedPrefs
) {
    fun getSharedPrefs(): SharedPreferences {
        return sharedPrefs.getSharedPrefs()
    }
}