package com.example.manifestofinal.signin

import android.app.Application
import android.util.Log
import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.manifestofinal.database.Guest
import com.example.manifestofinal.database.GuestDao
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val app: Application,
    private val GuestDao: GuestDao,
    private val dispatcher: Dispatchers
) : ViewModel() {
    private var viewModelJob = Job()

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    var passK: String = "x"
    fun getkey(theKey: String) {
        passK = theKey
    }

    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)
    var guest = MutableLiveData<Guest?>()

    private val _navigateToHome = MutableLiveData<Guest>()
    val navigateToHome: LiveData<Guest>
        get() = _navigateToHome

    private val _verify = MutableLiveData<Boolean>()
    val verify: LiveData<Boolean> = _verify

    init {
        initializeGuest()
    }

    fun verifyName(text: String): Boolean {
        if (text.length >= 3 && text.matches("^[a-zA-Z ]*$".toRegex()) && text.length <= 12) {
            return true
        }
        return false
    }

    fun verifyPhone(text: String): Boolean {
        if (Patterns.PHONE.matcher(text).matches()) {
            return true
        }
        return false
    }

    fun verifyEmail(text: String): Boolean {
        if (Patterns.EMAIL_ADDRESS.matcher(text).matches()) {
            return true
        }
        return false
    }

    fun verifyEmPhone(text: String): Boolean {
        if (Patterns.PHONE.matcher(text).matches()) {
            return true
        }
        return false
    }

    fun verifyEmName(text: String): Boolean {
        if (text.length >= 3 && text.matches("^[a-zA-Z ]*$".toRegex()) && text.length <= 12) {
            return true
        }
        return false
    }

    private fun initializeGuest() {
        uiScope.launch {
            guest.value = getGuestFromDatabase()
        }
    }

    private suspend fun getGuestFromDatabase(): Guest? {
        return withContext(Dispatchers.IO) {
            var guest = GuestDao.getLastGuest()
            guest
        }
    }
    fun onSaveGuest2(name2: String, phone2: String,email2: String, emergPhone: String, emergName: String) {
        uiScope.launch {
            if(passK != "x") {
                val newGuest = Guest(
                    guestID = passK.toLong(),
                    guestName = name2,
                    guestPhone = phone2,
                    guestEmail = email2,
                    guestEMphone = emergPhone,
                    guestEMname = emergName
                )
                update(newGuest)
            }
             else {
                val newGuest = Guest(
                    guestName = name2,
                    guestPhone = phone2,
                    guestEmail = email2,
                    guestEMphone = emergPhone,
                    guestEMname = emergName
                )
                insert(newGuest)
            }
            guest.value = getGuestFromDatabase()
            _navigateToHome.value = guest.value
        }
    }

    private suspend fun insert(guest: Guest) {
        withContext(Dispatchers.IO) {
            GuestDao.insert(guest)
        }
    }

    private suspend fun update(guest: Guest) {
        withContext(Dispatchers.IO) {
            GuestDao.update(guest)
        }
    }

}
