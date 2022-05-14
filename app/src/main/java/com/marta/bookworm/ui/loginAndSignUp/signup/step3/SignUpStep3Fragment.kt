package com.marta.bookworm.ui.loginAndSignUp.signup.step3

import android.graphics.Typeface
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.marta.bookworm.R
import com.marta.bookworm.databinding.FragmentSignUpStep3Binding
import com.marta.bookworm.ui.loginAndSignUp.signup.SignUpViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignUpStep3Fragment : Fragment() {

    private var _binding: FragmentSignUpStep3Binding? = null
    private val binding
        get() = _binding!!
    private val viewModel: SignUpStep3ViewModel by viewModels()
    private val viewModelActivity: SignUpViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSignUpStep3Binding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewLifecycleOwner.lifecycleScope.launchWhenCreated {
            viewModel.signUpStep3FUIState.collect { state ->
                renderUIState(state)
            }
        }
        setUI()
    }

    private fun setUI() {
        setBtn()
        setEditText()
    }

    private fun renderUIState(state: SignUpStep3UIState) {
        if (state.isError) showError(state.errorMsg)
        if (state.isSuccess) {
            inSuccess()
        }
    }

    private fun setBtn() {
        binding.btnContinue3.setOnClickListener {
            viewModel.validateAll(
                binding.etCountrySu.text.toString(),
                binding.etCitySu.text.toString(),
                binding.etAddressSu.text.toString(),
                binding.cbTermsConditions.isChecked
            )
        }
        binding.ibBack3.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun setEditText() {
        binding.etCitySu.onFocusChangeListener = View.OnFocusChangeListener { view, b ->
            if (b) hideError()
        }
        binding.etCountrySu.onFocusChangeListener = View.OnFocusChangeListener { view, b ->
            if (b) hideError()
        }
        binding.etAddressSu.onFocusChangeListener = View.OnFocusChangeListener { view, b ->
            if (b) hideError()
        }
    }

    private fun showError(text: String) {
        binding.tvErrorSu3.text = text
        binding.tvErrorSu3.visibility = View.VISIBLE
        highligthError()
    }

    private fun hideError() {
        binding.tvErrorSu3.text = ""
        binding.tvErrorSu3.visibility = View.GONE
        unhighligthError()
    }

    private fun highligthError() {
        binding.etCountrySu.setTextColor(resources.getColor(R.color.error))
        binding.etCountrySu.setTypeface(null, Typeface.BOLD)
        binding.etCitySu.setTextColor(resources.getColor(R.color.error))
        binding.etCitySu.setTypeface(null, Typeface.BOLD)
        binding.etAddressSu.setTextColor(resources.getColor(R.color.error))
        binding.etAddressSu.setTypeface(null, Typeface.BOLD)
    }

    private fun unhighligthError() {
        binding.etCountrySu.setTextColor(resources.getColor(R.color.shadow))
        binding.etCountrySu.setTypeface(null, Typeface.NORMAL)
        binding.etCitySu.setTextColor(resources.getColor(R.color.shadow))
        binding.etCitySu.setTypeface(null, Typeface.NORMAL)
        binding.etAddressSu.setTextColor(resources.getColor(R.color.shadow))
        binding.etAddressSu.setTypeface(null, Typeface.NORMAL)
    }

    private fun inSuccess() {
        viewModelActivity.setStepThreeData(
            binding.etCountrySu.text.toString(),
            binding.etCitySu.text.toString(),
            binding.etAddressSu.text.toString()
        )
        openDialog()
    }

    private fun openDialog() {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle("Welcome to Bookworm")
            .setMessage("Now you are one step apart from being part of our family. Check your email to validate your acount.")
            .setPositiveButton("Okay") { dialog, which ->
                navigateToLogin()
            }
            .show()
    }

    private fun navigateToLogin() {
        val action = SignUpStep3FragmentDirections.actionSignUpStep3FragmentToLoginFragment()
        findNavController().navigate(action)
    }
}