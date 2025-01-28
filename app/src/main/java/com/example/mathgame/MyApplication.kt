package com.example.mathgame

import android.app.Application
import android.content.Context
import com.example.mathgame.di.ActivityModule
import com.example.mathgame.di.ApplicationComponent
import com.example.mathgame.di.ApplicationModule
import com.example.mathgame.di.BaseContextModule
import com.example.mathgame.di.DaggerApplicationComponent

class MyApplication : Application() {

    lateinit var applicationComponent: ApplicationComponent
        private set

    override fun onCreate() {
        super.onCreate()

        applicationComponent = DaggerApplicationComponent
            .builder()
            .applicationModule(ApplicationModule(this))
            .build()
            applicationComponent.inject(this)
    }
}
