package com.marta.bookworm.ui.profile.settings

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.marta.bookworm.R
import com.marta.bookworm.databinding.FragmentSettingsBinding
import com.marta.bookworm.ui.profile.settings.editProfile.EditProfileFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SettingsFragment : Fragment() {
    private var _binding: FragmentSettingsBinding? = null
    private val binding: FragmentSettingsBinding get() = _binding!!
    private val viewModel: SettingsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSettingsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUI()
    }

    private fun setUI() {
        setOnClickListeners()
    }

    private fun setOnClickListeners() {
        with(binding){
            lyDelteAccount.setOnClickListener { viewModel.deleteAccount() }
            lyEditProfile.setOnClickListener { navigateToEditProfile() }
            lyLogOut.setOnClickListener { viewModel.logOut() }
            lyUpdatePsw.setOnClickListener { navigateToUpdatePsw() }
            ibSettingsBack.setOnClickListener { findNavController().popBackStack() }
        }
    }

    private fun navigateToUpdatePsw() {
        val action = SettingsFragmentDirections.actionSettingsFragmentToUpdatePasswordFragment2()
        findNavController().navigate(action)
    }

    private fun navigateToEditProfile() {
        val action = SettingsFragmentDirections.actionSettingsFragmentToEditProfileFragment2()
        findNavController().navigate(action)
    }
}