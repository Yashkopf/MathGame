package com.example.mathgame.di

import android.content.Context
import com.example.mathgame.MyApplication
import com.example.mathgame.presentation.GameFragment
import com.example.mathgame.presentation.MainActivity
import dagger.Component
import javax.inject.Singleton

@ActivityScope
@Component(dependencies = [ApplicationComponent::class], modules = [ActivityModule::class])
interface ActivityComponent {

    fun inject(activity: MainActivity)

    fun inject(fragment: GameFragment)

}