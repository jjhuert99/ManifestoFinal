package com.example.manifestofinal.signin

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
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
        val arguments = SignInFragmentArgs.fromBundle(requireArguments()).passedKey
        binding.lifecycleOwner = this
        binding.viewModelSignIn = viewModel
        viewModel.getkey(arguments)
        viewModel.fillFields()
        binding.backArrow.setOnClickListener{
            findNavController().popBackStack()
        }
        /*val herro = "HELLOOO"

        binding.editTextTextEmailAddress.setText(herro)
        binding.fullNameField2.setText(herro)*/

        //viewModel.name.value = "HELOOOO"
        binding.saveAndSignButton.setOnClickListener {
            if(!viewModel.verifyName()){
                binding.fullNameField.helperText = "Must be 2-12 characters long and have no special characters"
            }else{
                binding.fullNameField.isHelperTextEnabled = false
            }
            if(!viewModel.verifyPhone()){
                binding.phoneField.helperText = "Must be 10 digit number"
            }else{
                binding.phoneField.isHelperTextEnabled = false
            }
            if(!viewModel.verifyEmail()){
                binding.emailField.helperText = "Invalid email. Try again."
            }else{
                binding.emailField.isHelperTextEnabled = false
            }
            if(!viewModel.verifyEmPhone()){
                binding.emergencyPhoneField.helperText = "Must be 10 digit number"
            }else{
                binding.emergencyPhoneField.isHelperTextEnabled = false
            }
            if(!viewModel.verifyEmName()){
                binding.emergencyNameField.helperText = "Must be 2-12 characters long and have no special characters"
            }else{
                binding.emergencyNameField.isHelperTextEnabled = false
            }
            if(viewModel.verifyName() && viewModel.verifyPhone() && viewModel.verifyEmail() && viewModel.verifyEmName()){
                viewModel.onSaveGuest()
                findNavController().navigate(R.id.action_signInFragment_to_homeScreenFragment)
            }
        }

        return binding.root
    }

}
