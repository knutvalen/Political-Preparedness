package com.example.android.politicalpreparedness.election

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.android.politicalpreparedness.Repository
import kotlinx.coroutines.launch

class VoterInfoViewModel(
    application: Application,
    private val repository: Repository
) : AndroidViewModel(application) {

    val voterinfo = repository.voterinfo
    val savedElections = repository.savedElections

    init {

    }

    fun refreshVoterinfo(electionId: Int, address: String) = viewModelScope.launch {
        repository.refreshVoterinfo(electionId, address)
    }

    fun toggleSaveElection() {
        val election = voterinfo.value?.election ?: return

        savedElections.value?.let { savedElections ->
            val electionIDs = savedElections.map { it.id }

            if (electionIDs.contains(election.id)) {
                viewModelScope.launch {
                    repository.deleteElection(election.id)
                }

                return
            }
        }

        viewModelScope.launch {
            repository.saveElection(election)
        }
    }

}