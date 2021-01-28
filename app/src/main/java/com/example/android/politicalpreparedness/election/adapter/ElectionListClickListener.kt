package com.example.android.politicalpreparedness.election.adapter

class ElectionListClickListener(val clickListener: () -> Unit) {
    fun onClick() = clickListener()
}