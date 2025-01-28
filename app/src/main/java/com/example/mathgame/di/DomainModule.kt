package com.example.mathgame.di

import com.example.mathgame.data.GameRepositoryImpl
import com.example.mathgame.domain.repository.GameRepository
import dagger.Binds
import dagger.Module


@Module
interface DomainModule {

    @Binds
    fun bindRepository(impl: GameRepositoryImpl): GameRepository

}