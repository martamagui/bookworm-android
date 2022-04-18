package com.marta.bookworm.ui.signup.step1

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.marta.bookworm.R
import com.marta.bookworm.databinding.FragmentLoginBinding
import com.marta.bookworm.databinding.FragmentSignUpStep1Binding
import com.marta.bookworm.ui.login.LoginUIState
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignUpStep1Fragment : Fragment() {
    private var _binding: FragmentSignUpStep1Binding? = null
    private val binding
        get() = _binding!!
    private val viewModel: SignUpStep1ViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSignUpStep1Binding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewLifecycleOwner.lifecycleScope.launchWhenCreated {
            viewModel.signUpStep1UIState.collect { state ->
                renderUIState(state)
            }
        }
    }

    private fun renderUIState(state: SignUpStep1UIState) {
    }
}