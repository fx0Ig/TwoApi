package com.example.twoapi

import android.app.Application
import com.example.twoapi.data.UserDatabase

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        AppComponent.userDatabase = UserDatabase.getDatabase(this)
    }
}