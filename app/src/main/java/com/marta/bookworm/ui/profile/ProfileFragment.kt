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
import com.marta.bookworm.databinding.FragmentProfileBinding
import com.marta.bookworm.model.response.UserResponse
import com.marta.bookworm.ui.common.loadImage
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileFragment : Fragment() {
    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
    private val viewModel: ProfileViewModel by viewModels()
    private val args: ProfileFragmentArgs by navArgs()
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
            //TODO add in the API call the amount of followers and following
            tvProfileFollowingAmount.text = if(user.following == null){"0"}else{user.following.size.toString()}
            tvProfileFollowersAmount.text = if(user.followers!=null) ({user.followers}).toString() else{"0"}

            ivProfileAvatar.loadImage(user.avatar)
            ivBanner.loadImage(user.banner)
        }
    }

    private fun setUI() {

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