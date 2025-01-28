package com.example.mathgame.presentation

import android.app.Application
import android.content.Context
import android.os.CountDownTimer
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.mathgame.R
import com.example.mathgame.data.GameRepositoryImpl
import com.example.mathgame.domain.entity.GameResult
import com.example.mathgame.domain.entity.GameSettings
import com.example.mathgame.domain.entity.Level
import com.example.mathgame.domain.entity.Question
import com.example.mathgame.domain.repository.GameRepository
import com.example.mathgame.domain.usecases.GenerateQuestionUseCase
import com.example.mathgame.domain.usecases.GetGameSettingsUseCase
import javax.inject.Inject

class GameViewModel @Inject constructor(
    private val context: Context,
    private val repository: GameRepositoryImpl
) : ViewModel() {

    private var gameSettings: GameSettings? = null
    private var level: Level? = null

    private val generateQuestionUseCase = GenerateQuestionUseCase(repository)
    private val getGameSettingsUseCase = GetGameSettingsUseCase(repository)

    private var timer: CountDownTimer? = null

    private val _generateQuestion = MutableLiveData<Question>()
    val generateQuestion: LiveData<Question> = _generateQuestion

    private val _formattedTime = MutableLiveData<String>()
    val formattedTime: LiveData<String> = _formattedTime

    private val _percentOfRightAnswers = MutableLiveData<Int>()
    val percentOfRightAnswers: LiveData<Int> = _percentOfRightAnswers

    private val _progressAnswers = MutableLiveData<String>()
    val progressAnswers: LiveData<String> = _progressAnswers

    private val _enoughCountOfRightAnswers = MutableLiveData<Boolean>()
    val enoughCountOfRightAnswers: LiveData<Boolean> = _enoughCountOfRightAnswers

    private val _enoughPercentOfRightAnswers = MutableLiveData<Boolean>()
    val enoughPercentOfRightAnswers: LiveData<Boolean> = _enoughPercentOfRightAnswers

    private val _minPercent = MutableLiveData<Int>()
    val minPercent: LiveData<Int> = _minPercent

    private val _gameResult = MutableLiveData<GameResult>()
    val gameResult: LiveData<GameResult> = _gameResult

    private var countOfRightAnswers = 0
    private var countOfQuestions = 0


    fun startGame() {
        getGameSettings()
        generateQuestion()
        updateProgress()
    }

    fun chooseAnswer(number: Int) {
        checkAnswer(number)
        updateProgress()
        generateQuestion()
    }

    fun setLevel(level: Level?) {
        this.level = level
        startGame()
    }

    private fun updateProgress() {
        val percent = calculatePercentOfRightAnswers()
        _percentOfRightAnswers.value = percent
        _progressAnswers.value = String.format(
            context.resources.getString(R.string.percent_right_answers_game),
            countOfRightAnswers, gameSettings?.minCountOfRightAnswers
        )

        _enoughCountOfRightAnswers.value =
            countOfRightAnswers >= gameSettings?.minCountOfRightAnswers ?: 0
        _enoughPercentOfRightAnswers.value = if (percent == 0) true
            else percent >= gameSettings?.minPercentOfRightAnswers ?: 0
    }

    private fun calculatePercentOfRightAnswers(): Int {
        return ((countOfRightAnswers / countOfQuestions.toDouble()) * 100).toInt()
    }

    private fun checkAnswer(number: Int) {
        val rightAnswer = generateQuestion.value?.rightAnswer
        if (number == rightAnswer) {
            countOfRightAnswers++
        }
        countOfQuestions++
    }

    private fun getGameSettings() {
       level?.let { level ->
           this.gameSettings = getGameSettingsUseCase(level)
           _minPercent.value = gameSettings?.minPercentOfRightAnswers
           countOfQuestions = gameSettings?.minCountOfRightAnswers ?: 0
           startTimer()
       }
    }

    private fun startTimer() {
        timer = object : CountDownTimer(
            gameSettings?.gameTimeInSeconds?.times(MILLIS_IN_SECONDS) ?: 0,
            MILLIS_IN_SECONDS
        ) {
            override fun onTick(millisUntilFinished: Long) {
                _formattedTime.value = formatTime(millisUntilFinished)
            }

            override fun onFinish() {
                finishGame()
            }
        }
        timer?.start()
    }

    private fun generateQuestion() {
        _generateQuestion.value = generateQuestionUseCase(gameSettings?.maxSumValue ?: 0)
    }

    private fun formatTime(millisUntilFinished: Long): String {
        val seconds = millisUntilFinished / MILLIS_IN_SECONDS
        val minutes = seconds / SECONDS_IN_MINUTES
        val leftSeconds = seconds - (minutes * MILLIS_IN_SECONDS)
        return String.format("%02d:%02d", minutes, leftSeconds)
    }

    private fun finishGame() {
        _gameResult.value = GameResult(
            winner = _enoughPercentOfRightAnswers.value == true && _enoughCountOfRightAnswers.value == true,
            countOfRightAnswers = countOfRightAnswers,
            countOfQuestions = countOfQuestions,
            gameSettings = gameSettings
        )
    }

    override fun onCleared() {
        super.onCleared()
        timer?.cancel()
    }


    companion object {

        const val MILLIS_IN_SECONDS = 1000L
        const val SECONDS_IN_MINUTES = 60
    }

}