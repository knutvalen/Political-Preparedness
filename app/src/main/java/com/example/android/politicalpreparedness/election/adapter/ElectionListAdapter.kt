package com.example.android.politicalpreparedness.election.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.android.politicalpreparedness.R
import com.example.android.politicalpreparedness.databinding.ElectionListItemBinding
import com.example.android.politicalpreparedness.network.models.Election

class ElectionListAdapter(
        private val clickListener: ElectionListClickListener
) : RecyclerView.Adapter<ElectionListViewHolder>() {

    var elections: List<Election> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ElectionListViewHolder {
        val dataBinding: ElectionListItemBinding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.context), R.layout.election_list_item, parent, false
        )

        return ElectionListViewHolder(dataBinding)
    }

    override fun onBindViewHolder(holder: ElectionListViewHolder, position: Int) {
        holder.bind(clickListener, elections[position])
    }

    override fun getItemCount(): Int {
        return elections.size
    }

}