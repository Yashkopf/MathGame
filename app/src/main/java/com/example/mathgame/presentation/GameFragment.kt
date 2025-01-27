package com.example.mathgame.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.mathgame.R
import com.example.mathgame.databinding.FragmentGameBinding
import com.example.mathgame.domain.entity.GameResult
import com.example.mathgame.domain.entity.Level
import javax.security.auth.callback.Callback
import kotlin.concurrent.thread


class GameFragment : Fragment() {

    private var isTop = true
    private var level: Level? = null

    private val viewModel: GameViewModel by lazy {

        ViewModelProvider(
            this,
            GameViewModelFactory(level, requireActivity().application)
        )[GameViewModel::class.java]
    }

    private var _binding: FragmentGameBinding? = null
    private val binding: FragmentGameBinding
        get() = _binding ?: throw RuntimeException("FragmentGameBinding == null")

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentGameBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        level = arguments?.getParcelable(CHOOSE_LEVEL)
        initObservers()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initObservers() {
        viewModel.generateQuestion.observe(viewLifecycleOwner) {
            binding.tvLeftBoxAnswer.text = it.visibleNumber.toString()
            binding.tvMainNumber.text = it.sum.toString()

            val buttons = listOf<Button>(
                binding.btnAnswerOne,
                binding.btnAnswerTwo,
                binding.btnAnswerThree,
                binding.btnAnswerFour,
                binding.btnAnswerFive,
                binding.btnAnswerSix
            )
            buttons.forEachIndexed() { index, view ->
                view.text = it.options[index].toString()

                view.setOnClickListener {
                    viewModel.chooseAnswer(view.text.toString().toInt())
                }


//                        viewModel.generateQuestion.value?.rightAnswer == (test - view.text.toString().toInt())
//                            if (isTop) {
//                                binding.tvRightAnswerSmallTop.apply {
//                                    text = number[index].toString()
//                                    visibility = View.VISIBLE
//                                }
//                                binding.tvRightAnswerSmallBottom.visibility = View.INVISIBLE
//                            } else {
//                                binding.tvRightAnswerSmallBottom.apply {
//                                    text = number[index].toString()
//                                    visibility = View.VISIBLE
//                                }
//                                binding.tvRightAnswerSmallTop.visibility = View.INVISIBLE
//                            }
//                            isTop = !isTop
//                        } else {
//                            binding.tvRightAnswerSmallTop.visibility = View.INVISIBLE
//                            binding.tvRightAnswerSmallBottom.visibility = View.INVISIBLE
//                            isTop = true
//                        }

            }
        }

        viewModel.progressAnswers.observe(viewLifecycleOwner) {
            binding.tvCountOfRightAnswers.text = it
        }

        viewModel.percentOfRightAnswers.observe(viewLifecycleOwner) {
            binding.progressBar.setProgress(it, true)
        }

        viewModel.enoughPercentOfRightAnswers.observe(viewLifecycleOwner) { isEnough ->
            val colorRes = if (isEnough) android.R.color.holo_green_light
            else android.R.color.holo_red_light
            val color = ContextCompat.getColor(requireContext(), colorRes)

            binding.progressBar.setIndicatorColor(color)
        }

        viewModel.enoughCountOfRightAnswers.observe(viewLifecycleOwner) { isEnough ->
            if (isEnough) {
                binding.tvCountOfRightAnswers.setTextColor(
                    ContextCompat.getColor(
                        requireContext(),
                        android.R.color.holo_green_light
                    )
                )
            }
        }

        viewModel.formattedTime.observe(viewLifecycleOwner) {
            binding.tvTimer.text = it.toString()
        }

        viewModel.gameResult.observe(viewLifecycleOwner) {
            launchGameResultFragment(it)
        }
    }

    private fun launchGameResultFragment(gameResult: GameResult) {
        val rightPercent = viewModel.percentOfRightAnswers.value ?: -1
        findNavController().navigate(
            R.id.gameResultFragment,
            GameResultFragment.makeArgs(gameResult, rightPercent)
        )
    }

    companion object {

        const val CHOOSE_LEVEL = "choose_level"

        fun makeArgs(level: Level): Bundle {
            return bundleOf(CHOOSE_LEVEL to level)
        }
    }
}