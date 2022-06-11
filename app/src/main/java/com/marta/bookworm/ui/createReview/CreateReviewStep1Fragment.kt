package com.marta.bookworm.ui.createReview

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.marta.bookworm.databinding.FragmentCreateReviewStep1Binding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class CreateReviewStep1Fragment : Fragment() {
    private var _binding: FragmentCreateReviewStep1Binding? = null
    private val binding get() = _binding!!
    private val REQUEST_GALLERY = 1004
    private val viewModel: CreateReviewStep1ViewModel by viewModels()


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
                if(data?.data != null){
                   viewModel.uploadImage(data?.data!!)
                }
                hideGalleryIcon()
            }
        }
    }

    private fun setClicks() {
        binding.cvGallery.setOnClickListener { getImage() }
        binding.ivImagePreview.setOnClickListener { getImage() }
        binding.ibCreateReviewClose.setOnClickListener { goBack() }
        binding.cvContinueNew.setOnClickListener {
            storageInfo()
            navigateNext()
        }
    }

    private fun hideGalleryIcon(){
        binding.ivGalleryIcon.visibility = View.GONE
    }

    private fun storageInfo() {
        TODO("Not yet implemented")
    }

    private fun goBack(){
        findNavController().popBackStack()
    }

    private fun navigateNext() {
        TODO("Not yet implemented")
    }

}
