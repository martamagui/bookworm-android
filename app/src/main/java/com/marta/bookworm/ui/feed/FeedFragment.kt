package com.marta.bookworm.ui.feed

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.marta.bookworm.databinding.FragmentFeedBinding
import com.marta.bookworm.model.response.ReviewResponse
import com.marta.bookworm.ui.common.FeedAdapter

class FeedFragment : Fragment() {
    private var _binding: FragmentFeedBinding? = null
    private val binding get() = _binding!!
    private val viewModel: FeedViewModel by activityViewModels()
    private val adapter: FeedAdapter = FeedAdapter(
        { navigateToDetail(it) },
        { navigateToUserProfile(it) },
        { likePost(it) },
        { saveUnsavePost(it) },
        { openAmazon(it) }
    )

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
            viewModel.feedUIState.collect { feedUIState ->
                renderUIState(feedUIState)
            }
        }
        //TODO Change for the custom feed when the call is created.
        viewModel.getAllFeedPosts()
        setUI()
    }

    private fun setUI() {
        setAdapter()
        setBtns()
    }

    private fun setBtns() {
        with(binding) {
            ibNewReviewFeed.setOnClickListener { navigateToNewReview() }
            ibChatFeed.setOnClickListener { navigateToChat() }
        }
    }

    private fun setAdapter() {
        binding.rvFeed.adapter = adapter
        binding.rvFeed.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
    }

    private fun renderUIState(uiState: FeedUIState) {
        if (uiState.isError) {
            showError(uiState.errorMsg)
        }
        if (uiState.isLoading) {
            showLoadingAnimation()
        }
        if (uiState.isSuccess) {
            if (uiState.feedList!!.isEmpty()) {
                showEmptyBlock()
            } else {
                submitPostToAdapter(uiState.feedList)
            }
        }
    }

    private fun submitPostToAdapter(list: List<ReviewResponse>) {
        Log.d("hi", "${list.size}")
        adapter.submitList(list)
    }

    private fun showEmptyBlock() {
        Log.e("Empty", "Empty list")
        //TODO create an image or quote for empty lists
    }

    private fun showError(msg: String) {
        Log.d("Empty", "Error")
        MaterialAlertDialogBuilder(requireContext())
            .setTitle("Error Loading content")
            .setMessage("Connection error.")
            .setPositiveButton("Okay") { dialog, which -> }
            .show()
    }

    private fun showLoadingAnimation() {
        Log.d("Empty", "Animation")
        //TODO
    }

    //Network
    private fun likePost(postId: String) {
        viewModel.likePost(postId)
    }

    private fun saveUnsavePost(postId: String) {
        viewModel.saveUnsavePost(postId)
    }

    //Navigation
    private fun navigateToNewReview() {
        val action = FeedFragmentDirections.actionFeedFragment2ToCreateReviewStep1Fragment()
        findNavController().navigate(action)
    }

    private fun navigateToChat() {
        val action = FeedFragmentDirections.actionFeedFragment2ToChatListFragment()
        findNavController().navigate(action)
    }

    private fun navigateToDetail(postId: String) {
        val action = FeedFragmentDirections.actionFeedFragment2ToDetailFragment(postId)
        findNavController().navigate(action)
    }

    private fun navigateToUserProfile(userId: String) {
        val action = FeedFragmentDirections.actionFeedFragment2ToProfileFragment2(userId)
        findNavController().navigate(action)
    }

    private fun openAmazon(link: String) {
        val action = FeedFragmentDirections.actionFeedFragment2ToAmazonFragment(link)
        findNavController().navigate(action)
    }

}