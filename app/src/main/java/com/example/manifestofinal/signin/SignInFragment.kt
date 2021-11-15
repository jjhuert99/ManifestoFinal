package com.example.manifestofinal.signin

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.manifestofinal.R
import com.example.manifestofinal.databinding.SignInFragmentBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignInFragment : Fragment() {
    val viewModel: SignInViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = SignInFragmentBinding.inflate(inflater)
        binding.lifecycleOwner = this
        binding.viewModelSignIn = viewModel

        return binding.root
    }


}
