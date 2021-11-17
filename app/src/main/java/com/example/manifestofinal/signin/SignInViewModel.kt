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
    private val GuestDao: GuestDao
) : ViewModel() {
    private var viewModelJob = Job()

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    var passedKey: Long = 1L
    fun getkey(theKey: Long) {
        passedKey = theKey
    }

    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)
    var guest = MutableLiveData<Guest?>()
    private val _navigateToHome = MutableLiveData<Guest>()
    val navigateToHome: LiveData<Guest>
        get() = _navigateToHome

    val name = MutableLiveData<String>()
    val phone = MutableLiveData<String>()
    val email = MutableLiveData<String>()
    val emergencyPhone = MutableLiveData<String>()
    val emergencyName = MutableLiveData<String>()

    fun fillFields() {
        uiScope.launch {
            //not default value 1L, so a real value
            var newGuest = Guest()
            newGuest = fetchGuest(passedKey)
            if (passedKey != 1L) {
                name.value = newGuest.guestName
                phone.value = newGuest.guestPhone
                email.value = newGuest.guestEmail
                emergencyName.value = newGuest.guestEMname
                emergencyPhone.value = newGuest.guestEMphone
            }
        }
    }

    private val _verify = MutableLiveData<Boolean>()
    val verify: LiveData<Boolean> = _verify

    init {
        initializeGuest()
    }


    fun verifyName(): Boolean {
        var name2: String = ""
        if (!name.value.isNullOrEmpty()) {
            name2 = name.value.toString()
        }
        if (name2.length >= 3 && name2.matches("^[a-zA-Z ]*$".toRegex()) && name2.length <= 12) {
            return true
        }
        return false
    }

    fun verifyPhone(): Boolean {
        var phone2: String = ""
        if (!phone.value.isNullOrEmpty()) {
            phone2 = phone.value.toString()
        }
        if (Patterns.PHONE.matcher(phone2).matches()) {
            return true
        }
        return false
    }

    fun verifyEmail(): Boolean {
        var email2: String = ""
        if (!email.value.isNullOrEmpty()) {
            email2 = email.value.toString()
        }
        if (Patterns.EMAIL_ADDRESS.matcher(email2).matches()) {
            return true
        }
        return false
    }

    fun verifyEmPhone(): Boolean {
        var emP: String = ""
        if (!emergencyPhone.value.isNullOrEmpty()) {
            emP = emergencyPhone.value.toString()
        }
        if (Patterns.PHONE.matcher(emP).matches()) {
            return true
        }
        return false
    }

    fun verifyEmName(): Boolean {
        var emN: String = ""
        if (!emergencyName.value.isNullOrEmpty()) {
            emN = emergencyName.value.toString()
        }
        if (emN.length >= 3 && emN.matches("^[a-zA-Z ]*$".toRegex()) && emN.length <= 12) {
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

    fun onSaveGuest() {
        uiScope.launch {
            if (passedKey != 1L) {
                val newGuest = Guest(
                    guestName = name.value.toString(),
                    guestPhone = phone.value.toString(),
                    guestEmail = email.value.toString(),
                    guestEMphone = emergencyPhone.value.toString(),
                    guestEMname = emergencyName.value.toString()
                )
                update(newGuest)
                guest.value = getGuestFromDatabase()
                _navigateToHome.value = guest.value
            } else {
                val newGuest = Guest(
                    guestName = name.value.toString(),
                    guestPhone = phone.value.toString(),
                    guestEmail = email.value.toString(),
                    guestEMphone = emergencyPhone.value.toString(),
                    guestEMname = emergencyName.value.toString()
                )
                insert(newGuest)
                guest.value = getGuestFromDatabase()
                _navigateToHome.value = guest.value
            }
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

    private suspend fun fetchGuest(passedKey: Long): Guest {
        return withContext(Dispatchers.IO) {
            val aGuest = GuestDao.get(passedKey)
            aGuest
        }
    }

}
