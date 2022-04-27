package com.marta.bookworm.ui.signup.step1

import android.os.Bundle
import android.util.Log
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
import com.marta.bookworm.ui.signup.SignUpViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignUpStep1Fragment : Fragment() {
    private var _binding: FragmentSignUpStep1Binding? = null
    private val binding
        get() = _binding!!
    private val viewModel: SignUpViewModel by viewModels()

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
            //TODO
            Log.e("MailError", state.errorMssg);
        }
        if (state.isSuccess) {
            //TODO (cambiar de fragment)
        }
    }

    private fun setUI() {
        changeStatusBarColor()
        binding.btnContinue.setOnClickListener { viewModel }
        binding.etEmailSu.onFocusChangeListener = View.OnFocusChangeListener { view, b ->
            if (!b) {
                viewModel.validateEmail(binding.etEmailSu.text.toString())
            }else{
                viewModel.resetError()
            }
        }

    }

    private fun changeStatusBarColor() {
        //TODO find a better way to do this
        getActivity()?.let {
            getActivity()?.getWindow()?.setStatusBarColor(it.getColor(R.color.inverseOnSurface))
        };
    }
}