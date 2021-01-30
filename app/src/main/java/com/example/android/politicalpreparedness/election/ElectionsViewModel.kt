package com.example.android.politicalpreparedness.election

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.android.politicalpreparedness.Repository
import com.example.android.politicalpreparedness.database.ElectionDatabase
import kotlinx.coroutines.launch

//TODO: Construct ViewModel and provide election datasource
class ElectionsViewModel(application: Application, private val repository: Repository) : AndroidViewModel(application) {

    //TODO: Create live data val for upcoming elections

    //TODO: Create live data val for saved elections

    //TODO: Create val and functions to populate live data for upcoming elections from the API and saved elections from local database

    //TODO: Create functions to navigate to saved or upcoming election voter info

    val upcomingElections = repository.elections
    val savedElections = repository.electionsFollowed

    init {
        viewModelScope.launch {
            repository.refreshElections()
        }
    }

}