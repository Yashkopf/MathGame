package com.example.mathgame.data

import android.util.Log
import com.example.mathgame.domain.entity.GameSettings
import com.example.mathgame.domain.entity.Level
import com.example.mathgame.domain.entity.Question
import com.example.mathgame.domain.repository.GameRepository
import javax.inject.Inject
import kotlin.math.max
import kotlin.math.min
import kotlin.random.Random

class GameRepositoryImpl @Inject constructor () : GameRepository {

    override fun generateQuestion(
        maxSumValue: Int,
        countOfOptions: Int,
    ): Question {
        if (maxSumValue == 0) return Question(0,0, emptyList())
        Log.e("gere", "$maxSumValue, $countOfOptions")
        val sum = Random.nextInt(MIN_SUM_VALUE, maxSumValue)
        val visibleNumber = Random.nextInt(MIN_ANSWER_VALUE, sum)
        val options = HashSet<Int>()
        val rightAnswer = sum - visibleNumber
        options.add(rightAnswer)
        val from = max(rightAnswer - countOfOptions, MIN_ANSWER_VALUE)
        val to = min(maxSumValue, rightAnswer + countOfOptions)
        while (options.size < countOfOptions) {
            options.add(Random.nextInt(from, to))
        }
        return Question(sum, visibleNumber, options.toList())
    }

    override fun getGameSettings(level: Level): GameSettings {
        return when (level) {
            Level.TEST -> {
                GameSettings(10, 3, 50, 10)
            }

            Level.EASY -> {
                GameSettings(10, 5, 70, 10)
            }

            Level.NORMAL -> {
                GameSettings(20, 10, 80, 15)
            }

            Level.HARD -> {
                GameSettings(30, 10, 90, 20)
            }
        }
    }

    companion object {
        private const val MIN_SUM_VALUE = 2
        private const val MIN_ANSWER_VALUE = 1

    }
}