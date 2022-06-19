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
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.marta.bookworm.R
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
    private val adapter: ResultAdapter = ResultAdapter { navigateToDetail(it) }
    private var isFollowed: Boolean = false

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
            viewModel.profileUIState.collect { profileUIState ->
                renderUIState(profileUIState)
            }
        }
        setUI()
        viewModel.getProfileInfo(args.userId)
    }

    private fun renderUIState(state: ProfileUIState) {
        if (state.isSuccess && state.user != null) setUIData(state.user)
        if (state.isLoading && state.user == null) {
            showLoading(true)
        } else {
            showLoading(false)
        }
        if (state.isError) showError(state.errorMsg!!)
    }

    private fun showError(errorMsg: String) {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle("Error")
            .setMessage(errorMsg)
            .setPositiveButton("Okay") { dialog, which -> }
            .show()
    }

    private fun showLoading(loading: Boolean) {
        binding.shimmerProfile.visibility = if (loading) {
            View.VISIBLE
        } else {
            View.GONE
        }
        binding.rvProfile.visibility = if (!loading) {
            View.VISIBLE
        } else {
            View.GONE
        }
    }

    private fun setUIData(user: UserResponse) {
        with(binding) {
            tvProfileUsername.text = "@${user.userName}"
            tvProfileDescription.text = user.description
            tvProfileReviewsAmount.text = if (user.reviews == null) {
                "0"
            } else {
                user.reviews.size.toString()
            }
            tvProfileFollowingAmount.text = user.followingAmount.toString()
            tvProfileFollowersAmount.text = (user.followers).toString()
            ivProfileAvatar.loadImage(user.avatar)
            ivBanner.loadImage(user.banner)
            displayReviews(user.reviews)
            seBtns(user)
            if (user.isMe != null) setProfileMode(
                user.isMe!!,
                user.followed
            )
        }
    }

    private fun seBtns(user: UserResponse) {
        with(binding) {
            cvSettings.setOnClickListener { navigateToSettings() }
            cvSavedPosts.setOnClickListener { navigateToSavedPost() }
            cvFollowUnfollow.setOnClickListener { followAction(user) }
        }
    }

    private fun followAction(user: UserResponse) {
        viewModel.followUnfollow(user.id)
        isFollowed = !isFollowed
        if (isFollowed) {
            binding.ivFollow.setImageResource(R.drawable.ic_baseline_remove_24)
        } else {
            binding.ivFollow.setImageResource(R.drawable.ic_baseline_add_profile_24)
        }
    }

    private fun setProfileMode(isMe: Boolean, followed: Boolean?) {
        if (isMe) {
            binding.cvSavedPosts.visibility = View.VISIBLE
            binding.cvSettings.visibility = View.VISIBLE
        } else {
            isFollowed = followed ?: false
            binding.cvFollowUnfollow.visibility = View.VISIBLE
            binding.ivFollow.setImageResource(
                if (isFollowed) {
                    R.drawable.ic_baseline_remove_24
                } else {
                    R.drawable.ic_baseline_add_profile_24
                }
            )

        }
    }

    private fun displayReviews(list: List<ReviewResponse>?) {
        if (!list.isNullOrEmpty()) {
            adapter.submitList(list)
        } else {
            binding.tvEmptyProfile.visibility = View.VISIBLE
        }
    }

    private fun setUI() {
        with(binding) {
            rvProfile.adapter = adapter
            rvProfile.layoutManager = GridLayoutManager(context, 3)
            rvProfile.isNestedScrollingEnabled = false
            rvProfile.isScrollContainer = false
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

    private fun navigateToSavedPost() {
        val action = ProfileFragmentDirections.actionProfileFragment2ToSavedPostsFragment()
        findNavController().navigate(action)
    }

}