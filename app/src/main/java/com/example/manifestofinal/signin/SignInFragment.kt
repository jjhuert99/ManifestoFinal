package com.example.manifestofinal.signin

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.graphics.red
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
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
        var arguments = SignInFragmentArgs.fromBundle(requireArguments()).passedKey
        if(arguments == null)
        {
            arguments = arrayOf("x") }
        else{
            viewModel.getkey(arguments[0])
            if(arguments[0] != "x") {
                binding.fullNameField2.setText(arguments[1])
                binding.phoneField2.setText(arguments[2])
                binding.emailField2.setText(arguments[3])
                binding.emergencyPhoneField2.setText(arguments[4])
                binding.emergencyNameField2.setText(arguments[5])
            }
        }
        binding.lifecycleOwner = this
        binding.viewModelSignIn = viewModel

        binding.backArrow.setOnClickListener{
            findNavController().popBackStack()
        }

        binding.saveAndSignButton.setOnClickListener {
            if(!viewModel.verifyName(binding.fullNameField2.text.toString())){
                binding.fullNameField.helperText = getString(R.string.error_name)
            }else{
                binding.fullNameField.isHelperTextEnabled = false
            }
            if(!viewModel.verifyPhone(binding.phoneField2.text.toString())){
                binding.phoneField.helperText = getString(R.string.error_phone)
            }else{
                binding.phoneField.isHelperTextEnabled = false
            }
            if(!viewModel.verifyEmail(binding.emailField2.text.toString())){
                binding.emailField.helperText = getString(R.string.error_email)
            }else{
                binding.emailField.isHelperTextEnabled = false
            }
            if(!viewModel.verifyEmPhone(binding.emergencyPhoneField2.text.toString())){
                binding.emergencyPhoneField.helperText = getString(R.string.error_phone)
            }else{
                binding.emergencyPhoneField.isHelperTextEnabled = false
            }
            if(!viewModel.verifyEmName(binding.emergencyNameField2.text.toString())){
                binding.emergencyNameField.helperText = getString(R.string.error_name)
            }else{
                binding.emergencyNameField.isHelperTextEnabled = false
            }
            if(viewModel.verifyName(binding.fullNameField2.text.toString()) && viewModel.verifyPhone(binding.phoneField2.text.toString())
                && viewModel.verifyEmail(binding.emailField2.text.toString()) && viewModel.verifyEmName(binding.emergencyNameField2.text.toString())
                && viewModel.verifyEmPhone(binding.emergencyPhoneField2.text.toString())){
                        viewModel.onSaveGuest2(
                            binding.fullNameField2.text.toString(),
                            binding.phoneField2.text.toString(),
                            binding.emailField2.text.toString(),
                            binding.emergencyPhoneField2.text.toString(),
                            binding.emergencyNameField2.text.toString()
                        )
                findNavController().navigate(R.id.action_signInFragment_to_homeScreenFragment)
            }
        }

        return binding.root
    }
}
