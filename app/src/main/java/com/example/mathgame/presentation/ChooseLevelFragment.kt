package com.example.mathgame.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.mathgame.R
import com.example.mathgame.databinding.FragmentChooseLevelBinding
import com.example.mathgame.domain.entity.Level

class ChooseLevelFragment: Fragment() {

    private var _binding: FragmentChooseLevelBinding? = null
    private val binding: FragmentChooseLevelBinding
        get() = _binding ?: throw RuntimeException("FragmentGameBinding == null")


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentChooseLevelBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            buttonTest.setOnClickListener {
                launchGameFragment(Level.TEST)
            }
            buttonEasy.setOnClickListener {
                launchGameFragment(Level.EASY)
            }
            buttonNormal.setOnClickListener {
                launchGameFragment(Level.NORMAL)
            }
            buttonHard.setOnClickListener {
                launchGameFragment(Level.HARD)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun launchGameFragment (level: Level) {
        findNavController().navigate(R.id.gameFragment, GameFragment.makeArgs(level))
    }
}