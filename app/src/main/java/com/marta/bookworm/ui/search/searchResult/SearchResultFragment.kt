package com.marta.bookworm.ui.search.searchResult

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.marta.bookworm.R
import com.marta.bookworm.databinding.FragmentSearchResultBinding
import com.marta.bookworm.ui.common.ResultAdapter


class SearchResultFragment : Fragment() {
    private var _binding: FragmentSearchResultBinding? =  null
    private val binding get() = _binding!!
    private val adapter: ResultAdapter = ResultAdapter{ navigateToDetail(it) }
    private val args: SearchResultFragmentArgs by navArgs()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSearchResultBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUI()
    }

    private fun setUI(){
        with(binding){
            tvSearchValueSearch.text = args.searchValue
        }
    }

    private fun navigateToDetail(postId: String) {
        val action = SearchResultFragmentDirections.actionSearchResultFragmentToDetailFragment(postId)
        findNavController().navigate(action)
    }

}