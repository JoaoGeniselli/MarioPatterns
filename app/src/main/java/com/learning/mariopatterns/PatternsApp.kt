package com.learning.mariopatterns

import android.app.Application
import org.koin.core.context.startKoin

class PatternsApp : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            modules(Injection.module)
        }
    }
}