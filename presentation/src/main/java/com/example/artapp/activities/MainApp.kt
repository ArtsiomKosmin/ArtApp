package com.example.artapp.activities

import android.app.Application
import com.example.artapp.di.AppComponent
import com.example.artapp.di.AppModule
import com.example.artapp.di.DaggerAppComponent

class MainApp : Application() {
    lateinit var appComponent: AppComponent
    override fun onCreate() {
        super.onCreate()

        appComponent =  DaggerAppComponent
            .builder()
            .appModule(AppModule(context = this))
            .build()
    }
}