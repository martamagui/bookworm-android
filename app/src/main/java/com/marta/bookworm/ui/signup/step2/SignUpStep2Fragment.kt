package com.marta.bookworm.ui.signup.step2

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.DateValidatorPointBackward
import com.google.android.material.datepicker.DateValidatorPointForward
import com.google.android.material.datepicker.MaterialDatePicker
import com.marta.bookworm.R
import com.marta.bookworm.databinding.FragmentSignUpStep2Binding
import com.marta.bookworm.ui.signup.SignUpViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.time.format.DateTimeFormatter

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
        if (state.isError) showError(state.errorMssg)

        if (state.isSuccess) {
            //TODO (cambiar de fragment)
        }
        if(state.dob != "") setDobText(state.dob)
    }

    private fun setDobText(date:String) {
        binding.etDobSu2.setText(date)    }

    private fun showError(text: String) {
        //TODO show error function
    }

    private fun setUI(){
        binding.btnContinue2.setOnClickListener {
            viewModel.validateAll(binding.etDobSu2.text.toString(),binding.etPassword1.text.toString(), binding.etPassword2.text.toString())
        }
        binding.etDobSu2.setOnClickListener {
            openDatePicker()
        }
    }
    private fun openDatePicker() {
        val constraintsBuilder =
            CalendarConstraints.Builder()
                .setValidator(DateValidatorPointBackward.now())
                .build()
        val datePicker =
            MaterialDatePicker.Builder.datePicker()
                .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
                .setCalendarConstraints(constraintsBuilder)
                .setTitleText(R.string.dob)
                .build()
        datePicker.show(parentFragmentManager,"Dob")
        datePicker.addOnPositiveButtonClickListener {
            viewModel.setDob(it)
        }

    }

    private fun changeStatusBarColor() {
        //TODO find a better way to do this
        getActivity()?.let {
            getActivity()?.getWindow()?.setStatusBarColor(it.getColor(R.color.inverseOnSurface))
        };

    }
}