package com.example.manifestofinal.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.*
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

        val adapter = GuestListAdapter(GuestListener { guestID, theTag ->
            if (theTag == true) {
                viewModel.justNav()
                viewModel.pencilClick(guestID)
            } else {
                MaterialAlertDialogBuilder(this.requireContext())
                    .setMessage("Continue to delete {${guestID[0]}}")
                    .setNegativeButton("Cancel") { dialog, which ->
                    }
                    .setPositiveButton("Continue") { dialog, which ->
                        viewModel.onDeleteGuest(guestID[0].toLong())
                    }
                    .show()
            }
        })


        binding.guestList.adapter = adapter
        viewModel.navigateToEdit.observe(viewLifecycleOwner, Observer {
            it?.let {
                if(viewModel.navYet.value == true){
                this.findNavController().navigate(
                    HomeScreenFragmentDirections.actionHomeScreenFragmentToSignInFragment(it)
                )
            }
            viewModel.doneNav()
            }
        })

        viewModel.guests.observe(viewLifecycleOwner, Observer {
            it?.let {
                adapter.submitList(it)
            }
            if (adapter.itemCount <= 0) {
                binding.homeScreenMessege.visibility = VISIBLE
                binding.guestsPresentMessege.visibility = GONE
            } else {
                binding.homeScreenMessege.visibility = GONE
            }
        })

        return binding.root
    }
}
