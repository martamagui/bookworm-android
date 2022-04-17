package com.marta.bookworm.ui.signup.step1

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.marta.bookworm.R
import com.marta.bookworm.databinding.FragmentLoginBinding
import com.marta.bookworm.databinding.FragmentSignUpStep1Binding


class SignUpStep1Fragment : Fragment() {
    private var _binding: FragmentSignUpStep1Binding? = null
    private val binding
        get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSignUpStep1Binding.inflate(inflater, container, false)
        return binding.root
    }

}