package com.marta.bookworm.ui.top

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.marta.bookworm.R
import com.marta.bookworm.api.model.response.TopResponse
import com.marta.bookworm.databinding.FragmentTopBinding
import com.marta.bookworm.ui.common.loadImage
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TopFragment : Fragment() {
    private var _binding: FragmentTopBinding? = null
    private val binding get() = _binding!!
    private val viewModel: TopViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentTopBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewLifecycleOwner.lifecycleScope.launchWhenCreated {
            viewModel.topUIState.collect { topUIState ->
                renderUIState(topUIState)
            }
        }
        viewModel.getTopInfo()
    }

    private fun renderUIState(state: TopUIState) {
        if (state.isError) {
            showError(state.errorMsg)
        }
        if (state.isSuccess) {
            if (state.topResponse != null && state.topResponse?.size > 0) {
                setUI(state.topResponse)
            }
        }
        if (state.isLoading) {
            showLoadingAnimation()
        }
    }

    private fun showLoadingAnimation() {
        //TODO Shimmer animations
        Log.d("top", "loading")
    }

    private fun setUI(response: List<TopResponse>) {
        with(binding) {
            tvTop1Title.text = response[0]._id
            ivTop1Img.loadImage(response[0].reviews[0].image)
            ivTop1Img1.loadImage(response[0].reviews[0].image)
            ivTop1Img2.loadImage(response[0].reviews[0].image)
            //TODO change images when ther's more reviews on the DB
            tvTop2Title.text = response[1]._id
            ivTop2Img1.loadImage(response[1].reviews[0].image)
            ivTop2Img2.loadImage(response[1].reviews[0].image)
            ivTop2Img3.loadImage(response[1].reviews[0].image)

            tvTop3Title.text = response[2]._id
            ivTop3Img1.loadImage(response[2].reviews[0].image)
            ivTop3Img2.loadImage(response[2].reviews[0].image)
            ivTop3Img3.loadImage(response[2].reviews[0].image)
        }
        setClicks(response[0]._id, response[1]._id, response[2]._id)
    }

    private fun setClicks(title1:String, title2:String, title3:String) {
        with(binding){
            cvTop1.setOnClickListener { navigateToSearchByAuthor(title1)}
            cvTop2.setOnClickListener { navigateToSearchByAuthor(title2)}
            cvTop3.setOnClickListener { navigateToSearchByAuthor(title3)}

        }
    }

    private fun showError(msg: String?) {
        Log.d("top", "$msg")
        //TODO hacer un dialog que muestre el error
    }

    private fun navigateToSearchByAuthor(title: String){
        val action = TopFragmentDirections.actionTopFragment2ToSearchResultFragment(title,"title")
        findNavController().navigate(action)
    }
}




