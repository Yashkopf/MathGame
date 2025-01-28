package com.example.mathgame.domain.usecases

import com.example.mathgame.domain.entity.GameSettings
import com.example.mathgame.domain.entity.Level
import com.example.mathgame.domain.repository.GameRepository
import javax.inject.Inject

class GetGameSettingsUseCase @Inject constructor(
    private val repository: GameRepository
) {

    operator fun invoke(level: Level): GameSettings {
        return repository.getGameSettings(level)
    }
}