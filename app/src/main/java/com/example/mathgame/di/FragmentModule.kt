package com.example.mathgame.di

import com.example.mathgame.presentation.ChooseLevelFragment
import com.example.mathgame.presentation.GameFragment
import com.example.mathgame.presentation.GameResultFragment
import com.example.mathgame.presentation.WelcomeFragment
import dagger.Module
import dagger.Provides


@Module
class FragmentModule {

    @Provides
    fun gameFragment(): GameFragment{
        return GameFragment()
    }

    @Provides
    fun welcomeFragment(): WelcomeFragment {
        return WelcomeFragment()
    }

    @Provides
    fun chooseLevelFragment(): ChooseLevelFragment {
        return ChooseLevelFragment()
    }

    @Provides
    fun gameResultFragment(): GameResultFragment {
        return GameResultFragment()
    }
}