package com.marta.bookworm.ui.profile.savedPost

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.marta.bookworm.api.model.response.ReviewResponse
import com.marta.bookworm.databinding.FragmentSavedPostsBinding
import com.marta.bookworm.ui.common.ResultAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SavedPostsFragment : Fragment() {
    private var _binding: FragmentSavedPostsBinding? = null
    private val binding: FragmentSavedPostsBinding get() = _binding!!
    private val adapter: ResultAdapter = ResultAdapter{ navigateToDetail(it) }
    private  val viewModel : SavedPostViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSavedPostsBinding.inflate(inflater,container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewLifecycleOwner.lifecycleScope.launchWhenCreated {
            viewModel.savedPostUIState.collect{ savedPostsUIState ->
                renderUIState(savedPostsUIState)
            }
        }
        setUI()
        viewModel.getSavedPost()
    }

    private fun renderUIState(state: SavedPostUIState) {
        if(state.isError){
            showError(state.errorMsg)
        }
        if (state.isSuccess){
            displaySavedPosts(state.reviews)
        }
    }
    private fun setUI() {
        with(binding){
            rvSavedPosts.adapter = adapter
            rvSavedPosts.layoutManager = GridLayoutManager(context, 3)
        }
        binding.ibSavedPostBack.setOnClickListener { findNavController().popBackStack() }
    }

    private fun displaySavedPosts(reviews: List<ReviewResponse>?) {
        if(reviews!= null && reviews.size>0){
            adapter.submitList(reviews)
        }
    }

    private fun showError(errorMsg: String?) {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle("Error Loading content")
            .setMessage("Error: $errorMsg")
            .setPositiveButton("Okay") { dialog, which -> }
            .show()
    }


    private fun navigateToDetail(id: String) {
        val action = SavedPostsFragmentDirections.actionSavedPostsFragmentToDetailFragment(id)
        findNavController().navigate(action)
    }

}