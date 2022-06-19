package com.marta.bookworm.ui.detail

import android.content.res.ColorStateList
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.chip.Chip
import com.marta.bookworm.R
import com.marta.bookworm.databinding.FragmentDetailBinding
import com.marta.bookworm.api.model.response.ReviewResponse
import com.marta.bookworm.ui.common.loadImage
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailFragment : Fragment() {
    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!
    private val viewModel: DetailViewModel by viewModels()
    private val args: DetailFragmentArgs by navArgs()


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
            viewModel.detailUIState.collect { detailUIState ->
                renderUIState(detailUIState)
            }
        }
        viewModel.getPost(args.postId)
        setUI()
    }

    private fun setUI() {
        //TODO complete with userInfo
        Log.d("detail", "TODO")
    }

    private fun setUserInfo(review: ReviewResponse) {
        with(binding) {
            tvUsernameDetail.text = review.userId?.userName
            ivReviewImageDetail.loadImage(review.image)
            ibAvatarDetail.loadImage(review.userId?.avatar)
            tvScoreDetail.text = (review.score).toString()
            tvAuthorDetail.text = review.bookAuthor
            tvTitleDetail.text = review.bookTitle
            tvDescriptionDetail.text = review.reviewDescription
            if(review.userId!!.id == review.me){
                fabDeleteReview.visibility = View.VISIBLE
            }
        }
        setBtns(review)
        setTags(review.hastags)
        setLikeUI(review)
        setSavedUI(review)
    }

    private fun setTags(tags: List<String>) {
        tags.forEach { item ->
            createChip(item)
        }
    }

    private fun createChip(tag: String) {
        val chip = Chip(requireContext())
        chip.text = "#$tag"
        chip.isClickable = true
        chip.setOnClickListener {
            navigateToSearchResult(tag)
        }
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
        binding.chipGroupDetail.addView(chip as View)
    }

    private fun setBtns(review: ReviewResponse) {
        //TODO make hidden delete button if is not our post
        Log.d("detail", "TODObtns")
        with(binding) {
            ibDetailGoBack.setOnClickListener { findNavController().popBackStack() }
            layoutUserDetail.setOnClickListener { navigateToUserProfile(review.userId!!.id) }
            cvLikeDetail.setOnClickListener { likePost(review) }
            cvSaveDetail.setOnClickListener { saveUnsavePost(review) }
            cvShopDetail.setOnClickListener { openAmazon("$review.bookTitle") }
        }
    }

    private fun renderUIState(uiState: DetailUIState) {
        if (uiState.isError) {
            showError(uiState.errorMsg)
        }
        if (uiState.isLoading) {
            //TODO
            Log.d("detail", "cargando")
        }
        if (uiState.isSuccess && uiState.review != null) {
            setUserInfo(uiState.review)
        }
    }

    private fun showError(msg: String) {
        Log.e("Error", "Connection error")
    }

    private fun updateLike(post: ReviewResponse) {
        if (post.liked == true) {
            post.likesAmount = post.likesAmount?.minus(1)
        } else {
            post.likesAmount = post.likesAmount?.plus(1)
        }
        post.liked = post.liked == null || post.liked == false
    }

    private fun setLikeUI(post: ReviewResponse) {
        binding.ivLikeDetail.setImageResource(
            if (post.liked == true) {
                R.drawable.ic_baseline_favorite_24
            } else {
                R.drawable.ic_baseline_favorite_border_24
            }
        )
        binding.tvLikesAmountDetail.text = post.likesAmount.toString()
    }

    private fun updateSaved(post: ReviewResponse) {
        post.saved = post.saved == null || post.saved == false
    }

    private fun setSavedUI(post: ReviewResponse) {
        binding.ivSaveDetail.setImageResource(
            if (post.saved == true) {
                R.drawable.ic_baseline_bookmark_24
            } else {
                R.drawable.ic_baseline_bookmark_border_24
            }
        )
    }

    //Network
    private fun likePost(post: ReviewResponse) {
        updateLike(post)
        setLikeUI(post)
        viewModel.likePost(post.id)
    }

    private fun saveUnsavePost(post: ReviewResponse) {
        updateSaved(post)
        setSavedUI(post)
        viewModel.saveUnsavePost(post.id)
    }

    //Navigation
    private fun navigateToUserProfile(userId: String) {
        val action = DetailFragmentDirections.actionDetailFragmentToProfileFragment2(userId)
        findNavController().navigate(action)
    }

    private fun navigateToSearchResult(search: String) {
        val action =
            DetailFragmentDirections.actionDetailFragmentToSearchResultFragment(search, "tag")
        findNavController().navigate(action)
    }

    private fun openAmazon(link: String) {
        val action = DetailFragmentDirections.actionDetailFragmentToAmazonFragment(link)
        findNavController().navigate(action)
    }

}