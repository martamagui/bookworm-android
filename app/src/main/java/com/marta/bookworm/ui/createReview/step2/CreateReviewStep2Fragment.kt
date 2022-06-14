package com.marta.bookworm.ui.createReview.step2

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.marta.bookworm.R
import com.marta.bookworm.databinding.FragmentCreateReviewStep2Binding
import com.marta.bookworm.ui.createReview.CreateReviewSharedViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CreateReviewStep2Fragment : Fragment() {
    private var _binding: FragmentCreateReviewStep2Binding? = null
    private val binding get () = _binding
    private val viewModel: CreateReviewStep2ViewModel by viewModels()
    private val sharedViewModel: CreateReviewSharedViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCreateReviewStep2Binding.inflate(inflater, container, false)
        return binding!!.root
    }




}