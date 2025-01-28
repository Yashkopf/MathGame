package com.example.mathgame.di

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.mathgame.data.GameRepositoryImpl
import com.example.mathgame.presentation.GameViewModel
import com.example.mathgame.presentation.GameViewModelFactory
import dagger.Module
import dagger.Provides

@Module
class ActivityModule(private val activity: AppCompatActivity) {


    @Provides
    fun provideContext(): Context {
        return activity
    }

    @Provides
    fun provideGameViewModel(context: Context, gameRepository: GameRepositoryImpl): GameViewModel {
        return ViewModelProvider(
            activity,
            GameViewModelFactory(GameViewModel::class) {
                GameViewModel(context, gameRepository)
            })[GameViewModel::class.java]
    }

    @Provides
    fun provideFragment(fragment: Fragment): Fragment = fragment
}
