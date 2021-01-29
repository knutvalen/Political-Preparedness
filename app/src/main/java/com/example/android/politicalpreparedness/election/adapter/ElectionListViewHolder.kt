package com.example.android.politicalpreparedness.election.adapter

import androidx.recyclerview.widget.RecyclerView
import com.example.android.politicalpreparedness.databinding.ElectionListItemBinding
import com.example.android.politicalpreparedness.network.models.Election

class ElectionListViewHolder(
        var binding: ElectionListItemBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(clickListener: ElectionListClickListener, election: Election) {
        binding.clickListener = clickListener
        binding.election = election
        binding.executePendingBindings()
    }

}