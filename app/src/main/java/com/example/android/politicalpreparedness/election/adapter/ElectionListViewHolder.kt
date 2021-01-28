package com.example.android.politicalpreparedness.election.adapter

import androidx.recyclerview.widget.RecyclerView
import com.example.android.politicalpreparedness.databinding.ElectionListItemBinding

class ElectionListViewHolder(
        var binding: ElectionListItemBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(clickListener: ElectionListClickListener) {
        binding.clickListener = clickListener
        binding.executePendingBindings()
    }

}