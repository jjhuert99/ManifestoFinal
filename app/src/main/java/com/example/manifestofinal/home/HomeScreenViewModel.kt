package com.example.manifestofinal.home

import android.app.Application
import androidx.lifecycle.ViewModel
import com.example.manifestofinal.database.GuestDao
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeScreenViewModel @Inject constructor(
    private val app: Application,
    private val GuestDao: GuestDao
    ) : ViewModel() {

}
