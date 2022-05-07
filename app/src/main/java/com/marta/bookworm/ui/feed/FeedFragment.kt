package com.marta.bookworm.ui.feed

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.marta.bookworm.R
import com.marta.bookworm.databinding.FragmentFeedBinding
import com.marta.bookworm.model.response.ReviewResponse

class FeedFragment : Fragment() {
    private var _binding: FragmentFeedBinding? = null
    private val binding get() = _binding!!
    private val viewModel: FeedViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFeedBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewLifecycleOwner.lifecycleScope.launchWhenCreated {
            viewModel.feedUIState.collect{ feedUIState ->
                renderUIState(feedUIState)
            }
        }
    }

    private fun renderUIState(uiState: FeedUIState) {
        if(uiState.isError){
            showError(uiState.errorMsg)
        }
        if(uiState.isLoading){
            showLoadingAnimation()
        }
        if(uiState.isSuccess && uiState.feedList!= null){
            submitPostToAdapter(uiState.feedList)
        }
    }

    private fun submitPostToAdapter(list: List<ReviewResponse>) {
        if(list.size <=0){
            showEmptyBlock()
        }else{

        }
    }

    private fun showEmptyBlock() {
        TODO("Not yet implemented")
    }

    private fun showError(msg: String) {
        TODO("Not yet implemented")
    }

    private fun showLoadingAnimation() {
        TODO("Not yet implemented")
    }

}