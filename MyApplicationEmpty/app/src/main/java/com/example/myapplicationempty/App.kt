package com.example.myapplicationempty

import android.app.Application
import com.example.myapplicationempty.network.Api

class App: Application() {
    override fun onCreate() {
        super.onCreate()
        Api.INSTANCE = Api(this)
    }
}