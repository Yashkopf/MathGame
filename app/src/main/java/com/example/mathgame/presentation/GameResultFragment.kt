package com.example.mathgame.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.mathgame.R
import com.example.mathgame.databinding.FragmentResultBinding
import com.example.mathgame.domain.entity.GameResult
import javax.inject.Inject

class GameResultFragment @Inject constructor(): Fragment() {

    private var gameResult: GameResult? = null
    private var _binding: FragmentResultBinding? = null
    private val binding: FragmentResultBinding
        get() = _binding ?: throw RuntimeException("FragmentResultBinding == null")


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentResultBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        gameResult = arguments?.getParcelable<GameResult>(RESULT_ARG)
        val rightPercent = arguments?.getInt(RIGHT_PERCENT) ?: 0
        receiveScore(rightPercent)
        checkResult()
        onBackPressedCallBack()

        binding.btnTryAgain.setOnClickListener {
            retryGame()
        }
    }

    private fun onBackPressedCallBack() {
        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                retryGame()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner, callback)
    }

    private fun checkResult() {
        if (gameResult?.winner == true) {
            binding.ivLogoResult.setImageResource(R.drawable.thinkwin)
            binding.tvResult.text = getString(R.string.win_state)
        } else {
            binding.ivLogoResult.setImageResource(R.drawable.think_loose)
            binding.tvResult.text = getString(R.string.loose_state)
        }
    }

    private fun receiveScore(rightPercent: Int?) {
        binding.tvNecessaryAnswers.text = getString(
            R.string.necessary_right_answers_result,
            gameResult?.gameSettings?.minCountOfRightAnswers
        )
        binding.tvScore.text = getString(
            R.string.your_score_result,
            gameResult?.countOfRightAnswers
        )
        binding.tvNecessaryPercentAnswers.text = getString(
            R.string.necessary_percent_right_answers_result,
            gameResult?.gameSettings?.minPercentOfRightAnswers
        )
        binding.tvRightAnswersPercent.text = getString(
            R.string.right_answers_percent_result,
            rightPercent

        )
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun retryGame() {
        findNavController().popBackStack(R.id.chooseLevelFragment, false)
    }



    companion object {

        const val RESULT_ARG = "result_arg"
        const val RIGHT_PERCENT = "right_percent"

        fun makeArgs(gameResult: GameResult, rightPercent: Int): Bundle {
            return bundleOf(RESULT_ARG to gameResult, RIGHT_PERCENT to rightPercent)
        }

    }
}