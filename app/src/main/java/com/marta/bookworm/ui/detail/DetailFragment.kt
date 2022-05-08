package com.marta.bookworm.ui.detail

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.marta.bookworm.R
import com.marta.bookworm.databinding.FragmentDetailBinding

class DetailFragment : Fragment() {
    private var _binding = FragmentDetailBinding.inflate(layoutInflater)
    private val binding get() = _binding!!
    private val viewModel: DetailViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewLifecycleOwner.lifecycleScope.launchWhenCreated {
            viewModel.detailUIState.collect{ detailUIState ->
                renderUIState(detailUIState)
            }
        }
    }

    private fun renderUIState(uiState: DetailUIState) {
        if (uiState.isError) {
            showError(uiState.errorMsg)
        }
    }

    private fun showError(msg: String) {
        Log.e("Error", "Connection error")
    }

}