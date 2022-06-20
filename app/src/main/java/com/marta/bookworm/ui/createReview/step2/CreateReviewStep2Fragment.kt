package com.marta.bookworm.ui.createReview.step2

import android.content.res.ColorStateList
import android.os.Bundle
import android.view.KeyEvent
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.graphics.Color
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.commit
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.android.material.chip.Chip
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.marta.bookworm.R
import com.marta.bookworm.databinding.FragmentCreateReviewStep2Binding
import com.marta.bookworm.ui.createReview.CreateReviewSharedViewModel
import com.marta.bookworm.ui.feed.FeedFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CreateReviewStep2Fragment : Fragment() {
    private var _binding: FragmentCreateReviewStep2Binding? = null
    private val binding get() = _binding!!

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
        viewLifecycleOwner.lifecycleScope.launchWhenCreated {
            sharedViewModel.step2UIState.collect { state ->
                renderUIState(state)
            }
        }
        setEvents()
        setClicks()
    }

    private fun renderUIState(state: CreateReviewStep2UIState) {
        if (state.isError) {
            showError(state.errorMsg)
        }
        if (state.isSuccess) {
            navigateToFeed()
        }
    }

    private fun showError(msg: String) {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle("Error")
            .setMessage(msg)
            .setPositiveButton("Okay") { dialog, which ->
                binding.etDescription.isFocused
            }
            .show()
    }


    private fun setEvents() {
        tagEvent()
        publishEvent()
    }

    private fun setClicks() {
        with(binding) {
            btnPublish.setOnClickListener {
                publishEvent()
            }
            ibBack.setOnClickListener { navigateToFeed() }
        }
    }

    private fun tagEvent() {
        binding.etTags.setOnKeyListener(View.OnKeyListener { _, keyCode, event ->
            if (keyCode == KeyEvent.KEYCODE_ENTER && event.action == KeyEvent.ACTION_UP) {
                sharedViewModel.addTag(binding.etTags.text.toString())
                createChip(binding.etTags.text.toString())
                binding.etTags.setText("")
                return@OnKeyListener true
            }
            false
        })
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
        chip.setTextColor(
            ColorStateList.valueOf(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.primary
                )
            )
        )
        binding.cgTags.addView(chip as View)
    }

    private fun publishEvent() {
        binding.btnPublish.setOnClickListener {
            sharedViewModel.setStep2Info(binding.etDescription.text.toString())
        }
    }

    //------------ Navigation

    private fun navigateToFeed() {
        val action = CreateReviewStep2FragmentDirections.actionCreateReviewStep2FragmentToFeedFragment2()
        findNavController().navigate(action)
    }
}