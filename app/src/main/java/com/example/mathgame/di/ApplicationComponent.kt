package com.example.mathgame.di

import com.example.mathgame.MyApplication
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [FragmentModule::class,
        BaseContextModule::class,
        DataModule::class,
        ApplicationModule::class]
)
interface ApplicationComponent {

    fun inject(application: MyApplication)
}