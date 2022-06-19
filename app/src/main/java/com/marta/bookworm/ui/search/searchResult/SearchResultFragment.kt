package com.marta.bookworm.ui.search.searchResult

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.marta.bookworm.R
import com.marta.bookworm.api.model.response.ReviewResponse
import com.marta.bookworm.databinding.FragmentSearchResultBinding
import com.marta.bookworm.ui.common.ResultAdapter
import com.marta.bookworm.ui.top.TopUIState
import com.marta.bookworm.ui.top.TopViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchResultFragment : Fragment() {
    private var _binding: FragmentSearchResultBinding? = null
    private val binding get() = _binding!!
    private val adapter: ResultAdapter = ResultAdapter { navigateToDetail(it) }
    private val args: SearchResultFragmentArgs by navArgs()
    private val viewModel: SearchResultViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSearchResultBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewLifecycleOwner.lifecycleScope.launchWhenCreated {
            viewModel.searchResultUIState.collect { topUIState ->
                renderUIState(topUIState)
            }
        }
        setUI()
        viewModel.search(args.searchType, args.searchValue)
    }

    private fun renderUIState(state: SearchResultUIState) {
        if (state.isError) showError(state.errorMsg)

        if (state.isSuccess) {
            if (state.feedList != null && state.feedList?.size > 0) {
                setAdapterInfo(state.feedList)
                setEmptyTv(false)
            } else {
                setEmptyTv(true)
            }
        }
        if (state.isLoading) {
            showLoadingAnimation(true)
        } else {
            showLoadingAnimation(false)
        }

    }

    private fun setEmptyTv(visible: Boolean) {
        binding.tvEmptySearch.visibility = if (visible) {
            View.VISIBLE
        } else {
            View.GONE
        }
    }

    private fun setAdapterInfo(reviews: List<ReviewResponse>) {
        adapter.submitList(reviews)
    }

    private fun setUI() {
        with(binding) {
            tvSearchValueSearch.text = args.searchValue
            rvResultReviews.adapter = adapter
            rvResultReviews.layoutManager = GridLayoutManager(context, 3)
            setImageSearchType()
            ibSearchBack.setOnClickListener { findNavController().popBackStack() }
        }
    }

    private fun setImageSearchType() {
        with(binding) {
            if (args.searchType == "title") {
                ivSearchTypeSearch.setImageResource(R.drawable.ic_book_32)
            } else if (args.searchType == "author") {
                ivSearchTypeSearch.setImageResource(R.drawable.ic_feather_32)
            } else if (args.searchType == "tag") {
                ivSearchTypeSearch.setImageResource(R.drawable.ic_hashtag_32)
            }
        }
    }

    private fun showLoadingAnimation(empty: Boolean) {
        binding.shimmerSearch.visibility = if (empty) {
            View.VISIBLE
        } else {
            View.GONE
        }
    }

    private fun showError(msg: String?) {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle("Error")
            .setMessage(msg)
            .setPositiveButton("Okay") { dialog, which -> }
            .show()
    }

    private fun navigateToDetail(postId: String) {
        val action =
            SearchResultFragmentDirections.actionSearchResultFragmentToDetailFragment(postId)
        findNavController().navigate(action)
    }

}