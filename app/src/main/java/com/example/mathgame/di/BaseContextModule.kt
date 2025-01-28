package com.example.mathgame.di

import android.app.Application
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class BaseContextModule(private val application: Application) {

    @Provides
    @Singleton
    fun application(): Application {
        return application
    }
}