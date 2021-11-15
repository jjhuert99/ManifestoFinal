package com.example.manifestofinal.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "guest_table")
data class Guest(
    @PrimaryKey(autoGenerate = true)
    var guestID: Long = 0L,

    @ColumnInfo(name = "guest_name")
    var guestName: String = "",

    @ColumnInfo(name = "guest_phone")
    var guestPhone: String = "",

    @ColumnInfo(name = "guest_email")
    var guestEmail: String = "",

    @ColumnInfo(name = "guest_em_phone")
    var guestEMphone: String = "",

    @ColumnInfo(name = "guest_em_name")
    var guestEMname: String = ""
)
