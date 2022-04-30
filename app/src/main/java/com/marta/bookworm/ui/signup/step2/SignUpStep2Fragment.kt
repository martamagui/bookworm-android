package com.marta.bookworm.ui.signup.step2

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.marta.bookworm.R
import com.marta.bookworm.databinding.FragmentSignUpStep2Binding
import com.marta.bookworm.ui.signup.SignUpViewModel
import com.marta.bookworm.ui.signup.step1.SignUpStep1ViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignUpStep2Fragment : Fragment() {
    private var _binding: FragmentSignUpStep2Binding? = null
    private val binding
        get() = _binding!!

    private val viewModel: SignUpStep2ViewModel by viewModels()
    private val viewModelActivity: SignUpViewModel by activityViewModels()

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
        setUI()
    }

    private fun renderUIState(state: SignUpStep2UIState) {
        if (state.isError) {
            //TODO
        }
        if (state.isSuccess) {
            //TODO (cambiar de fragment)
        }
    }
    private fun setUI(){
        binding.btnContinue2.setOnClickListener {
            viewModel.validateAll(binding.etDaySu2.text.toString(),binding.etPassword1.text.toString(), binding.etPassword2.text.toString())
        }
    }

    private fun changeStatusBarColor() {
        //TODO find a better way to do this
        getActivity()?.let {
            getActivity()?.getWindow()?.setStatusBarColor(it.getColor(R.color.inverseOnSurface))
        };

    }
}