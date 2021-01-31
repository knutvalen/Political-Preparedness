package com.example.android.politicalpreparedness.election

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.android.politicalpreparedness.Repository
import com.example.android.politicalpreparedness.database.ElectionDatabase
import com.example.android.politicalpreparedness.network.models.Election
import kotlinx.coroutines.launch

//TODO: Construct ViewModel and provide election datasource
class ElectionsViewModel(application: Application, private val repository: Repository) : AndroidViewModel(application) {

    //TODO: Create live data val for upcoming elections

    //TODO: Create live data val for saved elections

    //TODO: Create val and functions to populate live data for upcoming elections from the API and saved elections from local database

    //TODO: Create functions to navigate to saved or upcoming election voter info

    val upcomingElections = repository.upcomingElections
    val savedElections = repository.savedElections
    private val _selectedElection = MutableLiveData<Election>()
    val selectedElection: LiveData<Election>
        get() = _selectedElection

    init {
        viewModelScope.launch {
            repository.refreshElections()
        }
    }

    fun navigateToElectionDetailsFragment(election: Election) {
        _selectedElection.value = election
    }

    fun navigateToElectionDetailsFragmentComplete() {
        _selectedElection.value = null
    }

}