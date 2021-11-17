package com.example.manifestofinal.home

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.manifestofinal.database.Guest
import com.example.manifestofinal.database.GuestDao
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class HomeScreenViewModel @Inject constructor(
    private val app: Application,
    private val GuestDao: GuestDao
    ) : ViewModel() {

    var guest = MutableLiveData<Guest?>()
    var guests = GuestDao.getAllGuest()

    init {
        initializeGuest()
        //updateList()
    }
    private fun initializeGuest(){
        viewModelScope.launch {
            guest.value = getGuestFromDatabase()
        }
    }
    private suspend fun getGuestFromDatabase(): Guest?{
        return withContext(Dispatchers.IO){
            var guest = GuestDao.getLastGuest()
            guest

        }
    }
    private val _navigateToEdit = MutableLiveData<Long>()
    val navigateToEdit
        get() = _navigateToEdit

    fun pencilClick(id: Long){
        _navigateToEdit.value = id
    }

    fun onDeleteGuest(guestID: Long){
        viewModelScope.launch {
            deleteGuest(guestID)
        }
    }

    private suspend fun deleteGuest(guestID: Long){
        withContext(Dispatchers.IO){
            GuestDao.clearOne(guestID)
        }
    }

}
