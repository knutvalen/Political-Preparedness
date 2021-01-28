package com.example.android.politicalpreparedness.election.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.android.politicalpreparedness.R
import com.example.android.politicalpreparedness.databinding.ElectionListItemBinding

class ElectionListAdapter(
        private val clickListener: ElectionListClickListener
) : RecyclerView.Adapter<ElectionListViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ElectionListViewHolder {
        val dataBinding: ElectionListItemBinding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.context), R.layout.election_list_item, parent, false
        )

        return ElectionListViewHolder(dataBinding)
    }

    override fun onBindViewHolder(holder: ElectionListViewHolder, position: Int) {
        holder.bind(clickListener)
    }

    override fun getItemCount(): Int {
        return 0
    }
}

//TODO: Bind ViewHolder

//TODO: Add companion object to inflate ViewHolder (from)

//TODO: Create ElectionViewHolder

//TODO: Create ElectionDiffCallback

//TODO: Create ElectionListener