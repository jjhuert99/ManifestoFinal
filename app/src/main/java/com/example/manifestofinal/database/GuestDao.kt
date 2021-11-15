package com.example.manifestofinal.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.*

@Dao
interface GuestDao {
    @Insert
    fun insert(guest: Guest)

    @Update
    fun update(guest: Guest)

    @Query("SELECT * from guest_table WHERE guestID = :key")
    fun get(key: Long): Guest

    @Query("DELETE FROM guest_table")
    fun clear()

    @Query("DELETE FROM guest_table WHERE guestID = :key")
    fun clearOne(key: Long)

    @Query("SELECT * FROM guest_table ORDER BY guestID DESC")
    fun getAllGuest(): LiveData<List<Guest>>

    @Query("SELECT * FROM guest_table ORDER BY guestID DESC LIMIT 1")
    fun getLastGuest(): Guest?
}
