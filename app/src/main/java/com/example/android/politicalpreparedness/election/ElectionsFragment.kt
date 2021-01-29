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

    private lateinit var binding: FragmentElectionBinding
    private val viewModel: ElectionsViewModel by viewModel()

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

        binding = FragmentElectionBinding.inflate(inflater)
        binding.viewModel = viewModel
        return binding.root
    }

    //TODO: Refresh adapters when fragment loads

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.lifecycleOwner = this

        val viewModelAdapterUpcomingElections = ElectionListAdapter(ElectionListClickListener {
            Timber.d("viewModelAdapterUpcomingElections")
        })

        val viewModelAdapterSavedElections = ElectionListAdapter(ElectionListClickListener {
            Timber.d("viewModelAdapterSavedElections")
        })

        binding.root.findViewById<RecyclerView>(R.id.recyclerViewUpcomingElections).apply {
            layoutManager = LinearLayoutManager(context)
            adapter = viewModelAdapterUpcomingElections
        }

        binding.root.findViewById<RecyclerView>(R.id.recyclerViewSavedElections).apply {
            layoutManager = LinearLayoutManager(context)
            adapter = viewModelAdapterSavedElections
        }

        viewModel.elections.observe(viewLifecycleOwner, { elections ->
            elections?.apply {
                viewModelAdapterUpcomingElections.elections = elections
            }
        })
    }

}