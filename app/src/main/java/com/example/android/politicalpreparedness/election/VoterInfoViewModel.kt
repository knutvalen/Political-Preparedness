package com.example.android.politicalpreparedness.election

import android.app.Application
import android.view.View
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.android.politicalpreparedness.Repository
import kotlinx.coroutines.launch

class VoterInfoViewModel(
    application: Application,
    private val repository: Repository
) : AndroidViewModel(application) {

    val voterinfo = repository.voterinfo
    val savedElections = repository.savedElections
    val apiError = repository.apiError
    val electionName = MutableLiveData<String?>()
    val electionDate = MutableLiveData<String?>()
    val votingLocationURL = MutableLiveData<String?>()
    val ballotURL = MutableLiveData<String?>()
    val correspondenceAddress = MutableLiveData<String?>()
    val buttonText = MutableLiveData<String?>()
    val electionInformationVisibility = MutableLiveData<Int>()
    val correspondenceVisibility = MutableLiveData<Int>()
    val buttonVisibility = MutableLiveData<Int>()

    init {
        electionInformationVisibility.value = View.GONE
        correspondenceVisibility.value = View.GONE
        buttonVisibility.value = View.GONE
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

    fun displayErrorMessageComplete() {
        repository.resetErrorMessage()
    }

}