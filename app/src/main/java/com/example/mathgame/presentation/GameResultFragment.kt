package com.example.mathgame.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import com.example.mathgame.R
import com.example.mathgame.databinding.FragmentResultBinding
import com.example.mathgame.domain.entity.GameResult

class GameResultFragment : Fragment() {

    private var gameResult: GameResult? = null
    private var rightAnswersPercent: Int? = null

    private var _binding: FragmentResultBinding? = null
    private val binding: FragmentResultBinding
        get() = _binding ?: throw RuntimeException("FragmentResultBinding == null")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        parseArgs()
    }

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
        receiveScore()
        checkResult()

        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                retryGame()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner, callback)
        binding.btnTryAgain.setOnClickListener {
            retryGame()
        }
    }

    private fun checkResult () {
        if (gameResult?.winner == true) {
            binding.ivLogoResult.setImageResource(R.drawable.thinkwin)
            binding.tvResult.text = getString(R.string.win_state)
        } else {
            binding.ivLogoResult.setImageResource(R.drawable.think_loose)
            binding.tvResult.text = getString(R.string.loose_state)
        }
    }

    private fun receiveScore() {
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
            rightAnswersPercent
        )
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun parseArgs() {
        requireArguments().getParcelable<GameResult>(RESULT_KEY)?.let {
            gameResult = it
        }
        rightAnswersPercent = requireArguments().getInt(ANSWERS_PERCENT_KEY)
    }

    private fun retryGame() {
        requireActivity().supportFragmentManager.popBackStack(GameFragment.NAME, 1)
    }

    companion object {

        const val RESULT_KEY = "result"
        const val ANSWERS_PERCENT_KEY = "answers_percent_key"

        fun newInstance(gameResult: GameResult, answersPercent: Int): GameResultFragment {
            return GameResultFragment().apply {
                arguments = Bundle().apply {
                    putInt(ANSWERS_PERCENT_KEY, answersPercent)
                    putParcelable(RESULT_KEY, gameResult)
                }
            }
        }
    }
}