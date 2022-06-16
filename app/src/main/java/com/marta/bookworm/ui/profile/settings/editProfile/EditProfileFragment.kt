package com.marta.bookworm.ui.profile.settings.editProfile

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.marta.bookworm.R
import com.marta.bookworm.databinding.FragmentEditProfileBinding
import com.marta.bookworm.databinding.FragmentProfileBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EditProfileFragment : Fragment() {
    private val REQUEST_GALLERY = 1004
    private var _binding: FragmentEditProfileBinding? = null
    private val binding get() = _binding!!
    private val viewModel: EditProfileViewModel by viewModels()
    private var avatarPressed = false
    private var bannerPressed = false


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentEditProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewLifecycleOwner.lifecycleScope.launchWhenCreated {
            viewModel.editProfileUIState.collect { state ->
                renderUI(state)
            }
        }
        setClicks()
    }

    private fun renderUI(state: EditProfileUIState) {
        if (state.isError) {
            //TODO
        }
    }

    private fun setClicks() {
        with(binding) {
            ivEdtiProfileAvatar.setOnClickListener {
                avatarPressed = true
                getImage()
            }
            ivEditBanner.setOnClickListener {
                bannerPressed = true
                getImage()
            }
            btnProfileChanges.setOnClickListener {
                viewModel.submitChanges(
                    etUsername,
                    etmlProfileDescription
                )
            }
        }
    }

    private fun getImage() {
        val intent = Intent(Intent.ACTION_GET_CONTENT)
            .setType("image/*")
            .addCategory(Intent.CATEGORY_OPENABLE)
        val mimeTypes =
            arrayOf("image/jpeg", "image/png")
        intent.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes)
        startActivityForResult(
            Intent.createChooser(intent, "Select"), REQUEST_GALLERY
        )
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == this.REQUEST_GALLERY && data?.data != null) {
            if (avatarPressed) {
                viewModel.setAvatarURI(data?.data!!)
            }
            if (bannerPressed) {
                viewModel.setBannerURI(data?.data!!)
            }
        }
    }


}