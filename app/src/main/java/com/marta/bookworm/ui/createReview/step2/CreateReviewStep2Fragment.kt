package com.marta.bookworm.ui.createReview.step2

import android.content.res.ColorStateList
import android.os.Bundle
import android.view.KeyEvent
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.google.android.material.chip.Chip
import com.marta.bookworm.R
import com.marta.bookworm.databinding.FragmentCreateReviewStep2Binding
import com.marta.bookworm.ui.createReview.CreateReviewSharedViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CreateReviewStep2Fragment : Fragment() {
    private var _binding: FragmentCreateReviewStep2Binding? = null
    private val binding get() = _binding!!
    private val viewModel: CreateReviewStep2ViewModel by viewModels()
    private val sharedViewModel: CreateReviewSharedViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCreateReviewStep2Binding.inflate(inflater, container, false)
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setEvents()
    }


    private fun createChip(tag: String) {
        val chip = Chip(requireContext())
        chip.text = "#$tag"
        chip.chipBackgroundColor =
            ColorStateList.valueOf(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.secondaryContainer
                )
            )
        chip.chipStrokeColor =
            ColorStateList.valueOf(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.secondaryContainer
                )
            )
        chip.setOnCloseIconClickListener {
            binding.cgTags.removeView(chip as View)
            //TODO remove from shared the tag
            //listTags.remove(text)
        }
        binding.cgTags.addView(chip as View)
    }

    private fun setEvents() {
        tagEvent()
        publishEvent()
    }

    private fun tagEvent() {
        binding.etTags.setOnKeyListener(View.OnKeyListener { _, keyCode, event ->
            if (keyCode == KeyEvent.KEYCODE_ENTER && event.action == KeyEvent.ACTION_UP) {
                createChip(binding.etTags.text.toString())
                binding.etTags.setText("")
                return@OnKeyListener true
            }
            false
        })
    }

    private fun publishEvent() {
        binding.btnPublish.setOnClickListener {
            //TODO publicar reseña
        }
    }


}