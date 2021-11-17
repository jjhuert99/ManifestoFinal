package com.example.manifestofinal.home

import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.example.manifestofinal.database.Guest

@BindingAdapter("guestNameMain")
fun TextView.setTheName(item: Guest?){
    item?.let{
        text = item.guestName
    }
}
