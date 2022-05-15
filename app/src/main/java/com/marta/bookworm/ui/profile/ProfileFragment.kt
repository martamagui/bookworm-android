package com.marta.bookworm.ui.profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import com.marta.bookworm.databinding.FragmentProfileBinding
import com.marta.bookworm.api.model.response.ReviewResponse
import com.marta.bookworm.api.model.response.UserResponse
import com.marta.bookworm.ui.common.ResultAdapter
import com.marta.bookworm.ui.common.loadImage
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileFragment : Fragment() {
    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
    private val viewModel: ProfileViewModel by viewModels()
    private val args: ProfileFragmentArgs by navArgs()
    private val adapter: ResultAdapter = ResultAdapter{ navigateToDetail(it) }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewLifecycleOwner.lifecycleScope.launchWhenCreated {
            viewModel.profileUIState.collect{ profileUIState ->
                renderUIState(profileUIState)
            }
        }
        setUI()
        viewModel.getProfileInfo(args.userId)
    }

    private fun renderUIState(state: ProfileUIState) {
       if(state.isSuccess && state.user!= null){
           setUIData(state.user)
       }
    }

    private fun setUIData(user: UserResponse) {
        with(binding){
            tvProfileUsername.text = "@${user.userName}"
            tvProfileDescription.text = user.description
            tvProfileReviewsAmount.text = if(user.reviews==null) {"0"} else{user.reviews.size.toString()}
            tvProfileFollowingAmount.text = user.followingAmount.toString()
            tvProfileFollowersAmount.text = (user.followers).toString()
            ivProfileAvatar.loadImage(user.avatar)
            ivBanner.loadImage(user.banner)
            displayReviews(user.reviews)
            user.isMe?.let { setProfileMode(it) }
        }
    }

    private fun setProfileMode(isMe: Boolean) {
        if(isMe){
            binding.cvSavedPosts.visibility = View.VISIBLE
            binding.cvSettings.visibility = View.VISIBLE
        }else{
            binding.cvFollowUnfollow.visibility = View.VISIBLE
            //TODO if there's time to create the chat view, uncomment.
            //binding.cvSendMessage.visibility = View.VISIBLE
        }
    }

    private fun displayReviews(list: List<ReviewResponse>?){
        if(list!=null && list.size>0){
            adapter.submitList(list)
        }else{
            //TODO show emptyLisBlock
        }
    }

    private fun setUI() {
        with(binding){
            rvProfile.adapter = adapter
            rvProfile.layoutManager = GridLayoutManager(context, 3)
        }
    }

    private fun navigateToSettings() {
        val action = ProfileFragmentDirections.actionProfileFragment2ToSettingsFragment()
        findNavController().navigate(action)
    }

    private fun navigateToDetail(postId: String) {
        val action = ProfileFragmentDirections.actionProfileFragment2ToDetailFragment(postId)
        findNavController().navigate(action)
    }

    private fun navigateToChat(userId: String) {
        //TODO
    }

}