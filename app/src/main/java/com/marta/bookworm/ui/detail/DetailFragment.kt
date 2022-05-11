package com.marta.bookworm.ui.detail

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.marta.bookworm.R
import com.marta.bookworm.databinding.FragmentDetailBinding
import com.marta.bookworm.model.response.ReviewResponse
import com.marta.bookworm.ui.common.loadImage
import com.marta.bookworm.ui.feed.FeedFragmentDirections

class DetailFragment : Fragment() {
    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!
    private val viewModel: DetailViewModel by activityViewModels()
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
            viewModel.detailUIState.collect{ detailUIState ->
                renderUIState(detailUIState)
            }
        }
        viewModel.getPost(args.postId)
        setUI()
    }

    private fun setUI() {
        //TODO complete with userInfo
       Log.d("detail", "TODO")
        setBtns()
    }

    private fun setUserInfo(review: ReviewResponse){
        with(binding){
            tvUsernameDetail.text = review.userId.userName
            ivReviewImageDetail.loadImage(review.image)
            ibAvatarDetail.loadImage(review.userId.avatar)
            tvScoreDetail.text = (review.score).toString()
            tvAuthorDetail.text = review.bookAuthor
            tvTitleDetail.text = review.bookTitle
            tvDescriptionDetail.text = review.reviewDescription
        }

    }

    private fun setTags(){
        //TODO complete with userInfo
        Log.d("detail", "TODO tags")
    }

    private fun setBtns(){
        //TODO complete with userInfo
        Log.d("detail", "TODObtns")
        with(binding){
            ibDetailGoBack.setOnClickListener { findNavController().popBackStack() }
            layoutUserDetail.setOnClickListener { navigateToUserProfile("TODO UserId") }
            cvLikeDetail.setOnClickListener { likePost("userid") }
            cvSaveDetail.setOnClickListener { saveUnsavePost("postId") }
        }
    }

    private fun renderUIState(uiState: DetailUIState) {
        if (uiState.isError) {
            showError(uiState.errorMsg)
        }
        if(uiState.isLoading){
            //TODO
            Log.d("detail", "cargando")
        }
        if(uiState.isSuccess && uiState.review!=null){
            setUserInfo(uiState.review)
        }
    }

    private fun showError(msg: String) {
        Log.e("Error", "Connection error")
    }

    //Network
    private fun likePost(postId: String) {
        viewModel.likePost(postId)
    }

    private fun saveUnsavePost(postId: String) {
        viewModel.saveUnsavePost(postId)
    }

    //Navigation
    private fun navigateToUserProfile(userId: String) {
        val action = DetailFragmentDirections.actionDetailFragmentToProfileFragment2(userId)
        findNavController().navigate(action)
    }

    // TODO Create a navigate to Tag Function
    // TODO Create a navigate Amazon funtion
}