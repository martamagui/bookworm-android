package com.marta.bookworm.ui.login

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.marta.bookworm.databinding.FragmentLoginBinding
import dagger.hilt.android.AndroidEntryPoint
import com.marta.bookworm.model.body.user.Credentials

@AndroidEntryPoint
class LoginFragment : Fragment() {
    private var _binding: FragmentLoginBinding? = null
    private val binding
        get() = _binding!!
    private val viewModel: LoginFragmentViewModel by viewModels()

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
        setUI()
    }

    private fun renderUIState(loginUIState: LoginUIState) {
        if(loginUIState.isError){
            showErrorMsg(loginUIState.errorMessage)
        }
        if(loginUIState.isSuccess){
        }
    }
    private fun setUI(){
        setBtnsListeners()
    }
    private fun setBtnsListeners(){
        binding.btnLogin.setOnClickListener { loginAction() }
        binding.btnAccountLogin.setOnClickListener { navigateToSignUp() }
    }

    private fun loginAction() {
        val email =  binding.etEmailLogin.text.toString()
        val password = binding.etPasswordLogin.text.toString()
        Log.e("Email ", "$password")
        if(email!=null && password!=null){
            val credentials = Credentials(email, password)
            viewModel.loginAction(credentials)
        }else{
            showErrorMsg("There is missing information")
        }
    }

    private fun showErrorMsg(message: String) {
       Log.e("e", "message")
    }

    private fun navigateToSignUp() {
        TODO("Not yet implemented")
    }

}