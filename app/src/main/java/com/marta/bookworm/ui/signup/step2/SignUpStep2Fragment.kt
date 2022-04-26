package com.marta.bookworm.ui.signup.step2

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.marta.bookworm.R
import com.marta.bookworm.databinding.FragmentSignUpStep2Binding
import com.marta.bookworm.ui.signup.SignUpViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignUpStep2Fragment : Fragment() {
    private var _binding: FragmentSignUpStep2Binding? = null
    private val binding
        get() = _binding!!
    private val viewModel: SignUpViewModel by viewModels()

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
            viewModel.signUpStep2FUIState.collect { state ->
                renderUIState(state)
            }
        }
    }

    private fun renderUIState(state: SignUpStep2UIState) {
        if (state.isError) {
            //TODO
        }
        if (state.isSuccess) {
            //TODO (cambiar de fragment)
        }
    }

    private fun changeStatusBarColor() {
        //TODO find a better way to do this
        getActivity()?.let {
            getActivity()?.getWindow()?.setStatusBarColor(it.getColor(R.color.inverseOnSurface))
        };

    }
}