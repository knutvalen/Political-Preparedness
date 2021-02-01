package com.example.android.politicalpreparedness.election.adapter

import androidx.recyclerview.widget.RecyclerView
import com.example.android.politicalpreparedness.databinding.ElectionListItemBinding
import com.example.android.politicalpreparedness.network.models.Election
import java.text.DateFormat
import java.util.*

class ElectionListViewHolder(
        var binding: ElectionListItemBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(clickListener: ElectionListClickListener, election: Election) {
        binding.clickListener = clickListener
        binding.election = election
        binding.textViewElectionListItemDate.text = DateFormat.getDateInstance(DateFormat.MEDIUM)
            .format(election.electionDay)
            .capitalize(Locale.getDefault())
        binding.executePendingBindings()
    }

}