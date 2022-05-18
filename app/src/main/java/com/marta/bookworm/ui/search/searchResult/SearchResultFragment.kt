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
        if (state.isError) {
            showError(state.errorMsg)
        }
        if (state.isSuccess) {
            if (state.feedList != null && state.feedList?.size > 0) {
                setAdapterInfo(state.feedList)
            }
        }
        if (state.isLoading) {
            showLoadingAnimation()
        }
    }

    private fun setAdapterInfo(reviews: List<ReviewResponse>) {
        if (reviews != null && reviews.size > 0) {
            adapter.submitList(reviews)
        } else {
            //TODO display empty block
        }
    }

    private fun setUI() {
        with(binding) {
            tvSearchValueSearch.text = args.searchValue
            rvResultReviews.adapter = adapter
            rvResultReviews.layoutManager = GridLayoutManager(context, 3)
        }
    }

    private fun showLoadingAnimation() {
        //TODO Shimmer animations
        Log.d("top", "loading")
    }

    private fun showError(msg: String?) {
        Log.d("top", "$msg")
        //TODO hacer un dialog que muestre el error
    }

    private fun navigateToDetail(postId: String) {
        val action =
            SearchResultFragmentDirections.actionSearchResultFragmentToDetailFragment(postId)
        findNavController().navigate(action)
    }

}