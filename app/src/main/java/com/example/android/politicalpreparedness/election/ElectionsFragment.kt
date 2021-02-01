package com.example.android.politicalpreparedness.election

import com.example.android.politicalpreparedness.election.adapter.ElectionListAdapter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
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
        binding = FragmentElectionBinding.inflate(inflater)
        binding.viewModel = viewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.lifecycleOwner = this

        val viewModelAdapterUpcomingElections = ElectionListAdapter(ElectionListClickListener {
            Timber.d("ElectionListClickListener: $it")
            viewModel.navigateToElectionDetailsFragment(it)
        })

        val viewModelAdapterSavedElections = ElectionListAdapter(ElectionListClickListener {
            Timber.d("ElectionListClickListener: $it")
            viewModel.navigateToElectionDetailsFragment(it)
        })

        binding.root.findViewById<RecyclerView>(R.id.recyclerViewUpcomingElections).apply {
            layoutManager = LinearLayoutManager(context)
            adapter = viewModelAdapterUpcomingElections
        }

        binding.root.findViewById<RecyclerView>(R.id.recyclerViewSavedElections).apply {
            layoutManager = LinearLayoutManager(context)
            adapter = viewModelAdapterSavedElections
        }

        viewModel.upcomingElections.observe(viewLifecycleOwner, { elections ->
            Timber.d(elections.toString())
            elections?.apply {
                viewModelAdapterUpcomingElections.elections = elections
            }
        })

        viewModel.savedElections.observe(viewLifecycleOwner, { elections ->
            Timber.d(elections.toString())
            elections?.apply {
                viewModelAdapterSavedElections.elections = elections
            }
        })

        viewModel.selectedElection.observe(viewLifecycleOwner, { election ->
            if (election != null) {
                findNavController().navigate(
                    ElectionsFragmentDirections.actionElectionsFragmentToVoterInfoFragment(
                        election.id,
                        election.division
                    )
                )

                viewModel.navigateToElectionDetailsFragmentComplete()
            }
        })
    }

}