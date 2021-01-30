package com.example.android.politicalpreparedness.election.adapter

import com.example.android.politicalpreparedness.network.models.Election

class ElectionListClickListener(val clickListener: (election: Election) -> Unit) {

    fun onClick(election: Election) = clickListener(election)

}