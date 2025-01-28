package com.example.mathgame.di

import android.app.Application
import com.example.mathgame.MyApplication
import com.example.mathgame.domain.entity.Level
import dagger.Module
import dagger.Provides

@Module
class ApplicationModule(private val application: MyApplication) {

    @Provides
    fun provideContext(): Application {
        return application
    }

}