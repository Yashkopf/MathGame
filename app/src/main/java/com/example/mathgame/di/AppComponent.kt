package com.example.mathgame.di

import android.app.Application
import com.example.mathgame.MainApplication
import com.example.mathgame.di.AppAnnotation.AppScope
import com.example.mathgame.presentation.GameFragment
import com.example.mathgame.presentation.MainActivity
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@AppScope
@Singleton
@Component(modules = [
    ViewModelModule::class,
    DomainModule::class
])
interface AppComponent {

    fun inject(app: MainApplication)

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance app: Application): AppComponent
    }

    fun inject(activity: MainActivity)

    fun injectGameFragment(fragment: GameFragment)
}