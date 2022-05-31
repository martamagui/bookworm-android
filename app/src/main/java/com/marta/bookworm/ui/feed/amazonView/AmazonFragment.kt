package com.marta.bookworm.ui.feed.amazonView

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.marta.bookworm.databinding.FragmentAmazonBinding
import java.text.Normalizer


class AmazonFragment : Fragment() {
    private var _binding: FragmentAmazonBinding? = null
    private val binding get() = _binding!!
    private val urlBase = "https://www.amazon.com/gp/mas/dl/android?s="
    private val args: AmazonFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAmazonBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var formattedSearch: String = Normalizer.normalize(args.link.lowercase(), Normalizer.Form.NFD)
        formattedSearch = formattedSearch.replace(" ", "%20")
//        formattedSearch = formattedSearch.replace("[^\\p{ASCII}]", "").replace(" ", "%20")
        setUI(formattedSearch)
    }

    private fun setUI(search: String) {
        binding.wbAmazon.loadUrl(urlBase + search)
        binding.ibAmazonGoBack.setOnClickListener { findNavController().popBackStack() }
    }

}