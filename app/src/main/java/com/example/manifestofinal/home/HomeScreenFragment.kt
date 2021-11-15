package com.example.manifestofinal.home

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.manifestofinal.R
import com.example.manifestofinal.databinding.HomeScreenFragmentBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeScreenFragment : Fragment() {

    val viewModel: HomeScreenViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = HomeScreenFragmentBinding.inflate(inflater)
        binding.lifecycleOwner = this
        binding.viewModelHome = viewModel

        return binding.root
    }
}
