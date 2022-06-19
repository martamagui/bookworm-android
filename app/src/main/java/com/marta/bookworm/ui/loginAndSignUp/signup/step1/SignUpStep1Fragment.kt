package com.marta.bookworm.ui.loginAndSignUp.signup.step1

import android.graphics.Typeface
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.marta.bookworm.R
import com.marta.bookworm.databinding.FragmentSignUpStep1Binding
import com.marta.bookworm.ui.loginAndSignUp.signup.SignUpViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignUpStep1Fragment : Fragment() {
    private var _binding: FragmentSignUpStep1Binding? = null
    private val binding
        get() = _binding!!
    private val viewModel: SignUpStep1ViewModel by viewModels()
    private val viewModelActivity: SignUpViewModel by activityViewModels()

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
            viewModel.signUpStep1FUIState.collect { state ->
                renderUIState(state)
            }
        }
        setUI()
    }


    private fun renderUIState(state: SignUpStep1UIState) {
        if (state.isError) {
            showError(state.errorMsg)
        } else {
            hideError()
        }
        if (state.isSuccess) {
            viewModelActivity.setStepOneData(
                binding.etEmailSu.text.toString(),
                binding.etUsernameSu.text.toString(),
                binding.etFullNameSu.text.toString()
            )
            navigateToNextStep()
            viewModel.resetSuccess()
        }
    }

    private fun setUI() {
        changeStatusBarColor()
        setBtn()
        setEditText()
    }

    private fun setEditText() {
        binding.etEmailSu.onFocusChangeListener = View.OnFocusChangeListener { view, b ->
            if (!b) {
                viewModel.validateEmail(binding.etEmailSu.text.toString())
            } else {
                viewModel.resetError()
            }
        }

        binding.etUsernameSu.onFocusChangeListener = View.OnFocusChangeListener { view, b ->
            if (!b) {
                viewModel.validateUserName(binding.etUsernameSu.text.toString())
            } else {
                viewModel.resetError()
            }
        }
    }

    private fun setBtn() {
        binding.btnContinue.setOnClickListener {
            viewModel.validateAll(
                binding.etEmailSu.text.toString(),
                binding.etUsernameSu.text.toString(),
                binding.etFullNameSu.text.toString()
            )
        }
        binding.ibBack1.setOnClickListener { findNavController().popBackStack() }
    }

    private fun changeStatusBarColor() {
        getActivity()?.let {
            getActivity()?.getWindow()?.setStatusBarColor(it.getColor(R.color.inverseOnSurface))
        };
    }

    private fun showError(text: String) {
        binding.tvError.visibility = View.VISIBLE
        binding.tvError.text = text
        highligthError(text)
    }

    private fun highligthError(text: String) {
        if (text == "Please introduce a valid userName") {
            binding.etUsernameSu.setTextColor(resources.getColor(R.color.error))
            binding.etUsernameSu.setTypeface(null, Typeface.BOLD)
        } else if (text == "Plase introduce a valid email") {
            binding.etEmailSu.setTextColor(resources.getColor(R.color.error))
            binding.etEmailSu.setTypeface(null, Typeface.BOLD)
        } else {
            binding.etFullNameSu.setTextColor(resources.getColor(R.color.error))
            binding.etFullNameSu.setTypeface(null, Typeface.BOLD)
        }
    }

    private fun unhighligthError() {
        binding.etUsernameSu.setTextColor(resources.getColor(R.color.shadow))
        binding.etUsernameSu.setTypeface(null, Typeface.NORMAL)
        binding.etEmailSu.setTextColor(resources.getColor(R.color.shadow))
        binding.etEmailSu.setTypeface(null, Typeface.NORMAL)
        binding.etFullNameSu.setTextColor(resources.getColor(R.color.shadow))
        binding.etFullNameSu.setTypeface(null, Typeface.NORMAL)
    }

    private fun hideError() {
        binding.tvError.visibility = View.GONE
        binding.tvError.text = ""
        unhighligthError()
    }

    private fun navigateToNextStep() {
        val action = SignUpStep1FragmentDirections.actionSignUpStep1FragmentToSignUpStep2Fragment();
        findNavController().navigate(action)
    }
}