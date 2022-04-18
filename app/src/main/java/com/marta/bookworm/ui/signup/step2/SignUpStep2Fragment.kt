package com.marta.bookworm.ui.signup.step2

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.marta.bookworm.R
import com.marta.bookworm.databinding.FragmentSignUpStep1Binding
import com.marta.bookworm.databinding.FragmentSignUpStep2Binding
import com.marta.bookworm.ui.signup.step1.SignUpStep1UIState
import com.marta.bookworm.ui.signup.step1.SignUpStep1ViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignUpStep2Fragment : Fragment() {
    private var _binding: FragmentSignUpStep2Binding? = null
    private val binding
        get() = _binding!!
    private val viewModel: SignUpStep2ViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSignUpStep2Binding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewLifecycleOwner.lifecycleScope.launchWhenCreated {
            viewModel.signUpStep2UIState.collect { state ->
                renderUIState(state)
            }
        }
    }

    private fun renderUIState(state: SignUpStep2UIState) {
    }
}