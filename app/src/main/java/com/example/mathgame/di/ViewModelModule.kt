package com.example.mathgame.di

import android.app.Application
import androidx.lifecycle.ViewModel
import com.example.mathgame.di.AppAnnotation.ViewModelKey
import com.example.mathgame.presentation.GameViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule() {

    @Binds
    abstract fun bindContext(app: Application): Application

    @Binds
    @IntoMap
    @ViewModelKey(GameViewModel::class)
    abstract fun bindRecipeViewModel(recipeViewModel: GameViewModel): ViewModel

}