package com.marta.bookworm.ui.loginAndSignUp.login

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.marta.bookworm.R
import com.marta.bookworm.databinding.FragmentLoginBinding
import dagger.hilt.android.AndroidEntryPoint
import com.marta.bookworm.model.body.user.Credentials

@AndroidEntryPoint
class LoginFragment : Fragment() {
    private var _binding: FragmentLoginBinding? = null
    private val binding
        get() = _binding!!
    private val viewModel: LoginFragmentViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewLifecycleOwner.lifecycleScope.launchWhenCreated {
            viewModel.loginUIState.collect { loginUIState ->
                renderUIState(loginUIState)
            }
        }
        checkToken()
        setUI()

    }

    private fun renderUIState(loginUIState: LoginUIState) {
        if (loginUIState.isError) {
            showErrorMsg(loginUIState.errorMessage)
        }
        if (loginUIState.isLoading) {
            showLoadingAnimation()
        }else{
            hideLoadingAnimation()
        }

        if (loginUIState.isSuccess) {
        }
    }

    private fun showLoadingAnimation() {
        binding.cardLoading.visibility = View.VISIBLE
    }

    private fun hideLoadingAnimation() {
        binding.cardLoading.visibility = View.GONE
    }

    private fun setUI() {
        changeStatusBarColor()
        setBtnsListeners()
        setEditTExt()
    }

    private fun setBtnsListeners() {
        binding.btnLogin.setOnClickListener { loginAction() }
        binding.btnAccountLogin.setOnClickListener { navigateToSignUp() }
    }

    private fun changeStatusBarColor() {
        //TODO find a better way to do this
        getActivity()?.let {
            getActivity()?.getWindow()?.setStatusBarColor(it.getColor(R.color.primary))
        };
    }

    private fun loginAction() {
        val email = binding.etEmailLogin.text.toString()
        val password = binding.etPasswordLogin.text.toString()
        Log.e("Email ", "$password")
        if (email != null && password != null) {
            val credentials = Credentials(email, password)
            viewModel.loginAction(credentials)
        } else {
            showErrorMsg("There is missing information")
        }
    }

    private fun showErrorMsg(message: String) {
        binding.tvLoginError.setText(message)
        binding.tvLoginError.visibility = View.VISIBLE
    }

    private fun hideError() {
        binding.tvLoginError.visibility = View.GONE
    }

    private fun setEditTExt(){
        binding.etEmailLogin.onFocusChangeListener = View.OnFocusChangeListener { view, b ->
            if (b) hideError()
        }
        binding.etPasswordLogin.onFocusChangeListener = View.OnFocusChangeListener { view, b ->
            if (b) hideError()
        }
    }

    private fun checkToken() {
        viewModel.thereIsAnyToken()
    }

    private fun navigateToSignUp() {
        val action = LoginFragmentDirections.actionLoginFragmentToSignUpStep1Fragment()
        findNavController().navigate(action)
    }


}