package com.marta.bookworm.ui.profile.settings.updatePsw

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.marta.bookworm.R
import com.marta.bookworm.databinding.FragmentUpdatePasswordBinding
import dagger.hilt.EntryPoint
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UpdatePasswordFragment : Fragment() {
    private var _binding: FragmentUpdatePasswordBinding? = null
    private val binding get() = _binding!!
    private val viewModel: UpdatePasswordViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentUpdatePasswordBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewLifecycleOwner.lifecycleScope.launchWhenCreated {
            viewModel.updatePasswordUIState.collect { state ->
                renderUI(state)
            }
        }
        setUI()
    }

    private fun setUI() {
        binding.ibUpdatePswBack.setOnClickListener { goBack() }
        binding.btnSavePwd.setOnClickListener {
            viewModel.updatePassword(
                binding.etNewPwd.text.toString(),
                binding.etRepeatNewPwd.text.toString()
            )
        }
    }

    private fun renderUI(state: UpdatePasswordUIState) {
        if (state.isSuccess) {
            showAlert("Changed password")
        }
        if (state.isError) {
            showAlert(state.errorMsg)
        }
        if (state.isLoading) {
            showHideLoading(true)
        }
        if (!state.isLoading) {
            showHideLoading(false)
        }
    }

    private fun showAlert(msg: String?) {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle("Alert")
            .setMessage(msg)
            .setPositiveButton("Okay") { dialog, which -> }
            .show()
    }

    private fun showHideLoading(visible: Boolean) {
        binding.loaderPsw.visibility = if (visible) {
            View.VISIBLE
        } else {
            View.GONE
        }
    }

    private fun goBack() {
        findNavController().popBackStack()
    }

}