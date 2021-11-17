package com.example.manifestofinal.home

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.example.manifestofinal.R
import com.example.manifestofinal.databinding.HomeScreenFragmentBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder
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
        binding.homeSignInButton.setOnClickListener { view: View ->
            view.findNavController().navigate(R.id.action_homeScreenFragment_to_signInFragment)
        }

        val adapter = GuestListAdapter(GuestListener { guestID, theTag, guestName ->
            if(theTag == true){
                viewModel.pencilClick(guestID)
            }
            else{
                MaterialAlertDialogBuilder(this.requireContext())
                    .setMessage("Continue to delete $guestName")
                    .setNegativeButton("Cancel") { dialog, which ->
                    }
                    .setPositiveButton("Continue") { dialog, which ->
                        viewModel.onDeleteGuest(guestID)
                    }
                    .show()
            }
        })


        binding.guestList.adapter = adapter
        viewModel.navigateToEdit.observe(viewLifecycleOwner, Observer{
            it?.let{
                this.findNavController().navigate(HomeScreenFragmentDirections.actionHomeScreenFragmentToSignInFragment(it))
            }
        })

        viewModel.guests.observe(viewLifecycleOwner, Observer{
            it?.let{
                adapter.submitList(it)
            }
        })

        return binding.root
    }
}
