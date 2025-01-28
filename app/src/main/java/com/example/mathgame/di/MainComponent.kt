package com.example.mathgame.di

import com.example.mathgame.presentation.GameFragment
import com.example.mathgame.presentation.MainActivity
import dagger.Component

@AppAnnotation.ActivityScope
@Component(modules = [ViewModelModule::class, DomainModule::class])
interface MainComponent {

    fun inject(activity: MainActivity)

    fun injectGameFragment(fragment: GameFragment)

}