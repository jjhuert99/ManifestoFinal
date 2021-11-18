package com.example.manifestofinal.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.manifestofinal.database.Guest
import com.example.manifestofinal.databinding.GuestViewholderBinding

class GuestListAdapter(val clickListener: GuestListener): ListAdapter<Guest, GuestListAdapter.ViewHolder>(GuestStayDiffCallBack()){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item, clickListener)
        holder.bind(getItem(position)!!, clickListener)
    }

    class ViewHolder private constructor(val binding: GuestViewholderBinding):RecyclerView.ViewHolder(binding.root){

        fun bind(item: Guest, clickListener: GuestListener) {
            binding.guest = item
            binding.clickListener = clickListener
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = GuestViewholderBinding.inflate(layoutInflater, parent, false)

                return ViewHolder(binding)
            }
        }
    }
}

class GuestStayDiffCallBack: DiffUtil.ItemCallback<Guest>(){
    override fun areItemsTheSame(oldItem: Guest, newItem: Guest): Boolean {
        return oldItem.guestID == newItem.guestID
    }

    override fun areContentsTheSame(oldItem: Guest, newItem: Guest): Boolean {
        return oldItem == newItem
    }
}

class GuestListener(val clickListener:(valuess: Array<String>, tag: Boolean) -> Unit){
    fun onClick(guest: Guest, tag: Boolean) = clickListener(arrayOf(guest.guestID.toString(), guest.guestName, guest.guestPhone, guest.guestEmail, guest.guestPhone, guest.guestEMname), tag)
}

