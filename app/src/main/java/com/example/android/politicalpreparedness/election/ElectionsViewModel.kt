package com.example.android.politicalpreparedness.election

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.android.politicalpreparedness.Repository
import com.example.android.politicalpreparedness.network.models.Election
import kotlinx.coroutines.launch

class ElectionsViewModel(
    application: Application,
    private val repository: Repository
) : AndroidViewModel(application) {

    val upcomingElections = repository.upcomingElections
    val savedElections = repository.savedElections

    private val _selectedElection = MutableLiveData<Election>()
    val selectedElection: LiveData<Election>
        get() = _selectedElection

    init {
        viewModelScope.launch {
            repository.refreshUpcomingElections()
        }
    }

    fun navigateToElectionDetailsFragment(election: Election) {
        _selectedElection.value = election
    }

    fun navigateToElectionDetailsFragmentComplete() {
        _selectedElection.value = null
    }

}