package com.example.mathgame.di

import com.example.mathgame.data.GameRepositoryImpl
import com.example.mathgame.domain.repository.GameRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DataModule {

    @Provides
    @Singleton
    fun provideRepository(impl: GameRepositoryImpl): GameRepository {
        return impl
    }
}