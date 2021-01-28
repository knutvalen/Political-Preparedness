package com.example.android.politicalpreparedness.election

import com.example.android.politicalpreparedness.election.adapter.ElectionListAdapter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.android.politicalpreparedness.R
import com.example.android.politicalpreparedness.databinding.FragmentElectionBinding
import com.example.android.politicalpreparedness.election.adapter.ElectionListClickListener
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber

class ElectionsFragment : Fragment() {

    //TODO: Declare ViewModel

    private val viewModel: ElectionsViewModel by viewModel()
    private var viewModelAdapterUpcomingElections: ElectionListAdapter? = null
    private var viewModelAdapterSavedElections: ElectionListAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        //TODO: Add ViewModel values and create ViewModel

        //TODO: Add binding values

        //TODO: Link elections to voter info

        //TODO: Initiate recycler adapters

        //TODO: Populate recycler adapters

        viewModelAdapterUpcomingElections = ElectionListAdapter(ElectionListClickListener {
            Timber.d("viewModelAdapterUpcomingElections")
        })

        viewModelAdapterSavedElections = ElectionListAdapter(ElectionListClickListener {
            Timber.d("viewModelAdapterSavedElections")
        })

        val binding = FragmentElectionBinding.inflate(inflater)

        binding.root.findViewById<RecyclerView>(R.id.recyclerViewUpcomingElections).apply {
            layoutManager = LinearLayoutManager(context)
            adapter = viewModelAdapterUpcomingElections
        }

        binding.root.findViewById<RecyclerView>(R.id.recyclerViewSavedElections).apply {
            layoutManager = LinearLayoutManager(context)
            adapter = viewModelAdapterSavedElections
        }

        return binding.root
    }

    //TODO: Refresh adapters when fragment loads

}