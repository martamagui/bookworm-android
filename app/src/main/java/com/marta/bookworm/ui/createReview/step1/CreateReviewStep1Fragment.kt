package com.marta.bookworm.ui.createReview.step1

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.marta.bookworm.databinding.FragmentCreateReviewStep1Binding
import com.marta.bookworm.ui.createReview.CreateReviewSharedViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class CreateReviewStep1Fragment : Fragment() {
    private var _binding: FragmentCreateReviewStep1Binding? = null
    private val binding get() = _binding!!
    private val REQUEST_GALLERY = 1004
    private val viewModel: CreateReviewStep1ViewModel by viewModels()
    private val sharedViewModel: CreateReviewSharedViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCreateReviewStep1Binding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setClicks()
    }

    //--------- SetUI

    private fun setClicks() {
        binding.cvGallery.setOnClickListener { getImage() }
        binding.ivImagePreview.setOnClickListener { getImage() }
        binding.ibCreateReviewClose.setOnClickListener { goBack() }
        binding.cvContinueNew.setOnClickListener {
            storageInfo()
            navigateNext()
        }
    }


    //------------ UIChanges

    private fun showError(msg: String) {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle("Error")
            .setMessage(msg)
            .show()
    }

    private fun hideGalleryIcon() {
        binding.ivGalleryIcon.visibility = View.GONE
    }

    //------------ Actions
    private fun storageInfo() {
        with(binding) {
            val author: String = etAuthor.text.toString()
            val title: String = etTitle.text.toString()
            val imageLink: String? = viewModel.createReviewUIState.value.imageLink
            if (author.length > 2 && title.length > 1 && !imageLink.isNullOrEmpty()) {
                sharedViewModel.setStep1Info(title, author, imageLink)
                navigateNext()
            } else {
                showError("Please, fill all the fields.")
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
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == this.REQUEST_GALLERY) {
                this.binding.ivImagePreview.setImageURI(data?.data)
                if (data?.data != null) {
                    viewModel.uploadImage(data?.data!!)
                }
                hideGalleryIcon()
            }
        }
    }

    //------------ Navigation

    private fun goBack() {
        findNavController().popBackStack()
    }

    private fun navigateNext() {
        val action =
            CreateReviewStep1FragmentDirections.actionCreateReviewStep1FragmentToCreateReviewStep2Fragment()
        findNavController().navigate(action)
    }

}
