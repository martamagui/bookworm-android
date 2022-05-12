package com.marta.bookworm.ui.common.amazonView

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.marta.bookworm.R
import com.marta.bookworm.databinding.FragmentAmazonBinding


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
        val search = args.link.lowercase().replace(" ", "%20")
        Log.d("Link", "$urlBase$search")
        binding.wbAmazon.loadUrl(urlBase+search)
    }

}