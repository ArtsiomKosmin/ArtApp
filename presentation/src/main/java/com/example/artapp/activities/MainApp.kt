package com.example.artapp.activities

import android.app.Application
import com.example.data.source.local.AppDataBase

class MainApp : Application() {
    val database by lazy { AppDataBase.getDataBase(this) }

}