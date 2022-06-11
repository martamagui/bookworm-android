package com.marta.bookworm.ui.createReview

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.marta.bookworm.R
import com.marta.bookworm.databinding.FragmentCreateReviewStep1Binding


class CreateReviewStep1Fragment : Fragment() {
    private var _binding: FragmentCreateReviewStep1Binding? = null
    private val binding get() = _binding!!
    private  val REQUEST_GALLERY = 1004

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCreateReviewStep1Binding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.cvGallery.setOnClickListener { getImage() }
    }

    private fun getImage() {
        val intent = Intent(Intent.ACTION_GET_CONTENT)
            .setType("image/*")
            .addCategory(Intent.CATEGORY_OPENABLE)
        val mimeTypes =
            arrayOf("image/jpeg", "image/png")
        intent.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes)
        startActivityForResult(
            Intent.createChooser(
                intent,
                "Select"
            ), REQUEST_GALLERY
        )
    }


}