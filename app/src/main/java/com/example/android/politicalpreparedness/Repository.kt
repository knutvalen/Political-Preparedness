package com.example.android.politicalpreparedness

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.android.politicalpreparedness.database.ElectionDao
import com.example.android.politicalpreparedness.network.CivicsApi
import com.example.android.politicalpreparedness.network.models.Election
import com.example.android.politicalpreparedness.network.models.VoterInfoResponse
import kotlinx.coroutines.*
import timber.log.Timber

class Repository(
    private val dataAccessObject: ElectionDao,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) {
    private val _upcomingElections = MutableLiveData<List<Election>>()
    val upcomingElections: LiveData<List<Election>>
        get() = _upcomingElections

    val savedElections: LiveData<List<Election>> = dataAccessObject.getElections()

    private val _voterinfo = MutableLiveData<VoterInfoResponse>()
    val voterinfo: LiveData<VoterInfoResponse>
        get() = _voterinfo

    suspend fun refreshUpcomingElections() = withContext(ioDispatcher) {
        try {
            val response = CivicsApi
                .retrofitService
                .getElectionsAsync()
                .await()

            withContext(Dispatchers.Main) {
                _upcomingElections.value = response.elections
            }
        } catch (e: Exception) {
            //TODO: do not swallow
            Timber.e(e)
        }
    }

    suspend fun refreshVoterinfo(electionId: Int, address: String) = withContext(ioDispatcher) {
        Timber.d("electionId: $electionId, address: $address")
        try {
            val response = CivicsApi
                .retrofitService
                .getVoterinfoAsync(electionId, address.plus(" ny"))
                .await()

            withContext(Dispatchers.Main) {
                _voterinfo.value = response
            }
        } catch (e: Exception) {
            //TODO: do not swallow
            Timber.e(e)
        }
    }

    suspend fun saveElection(election: Election) = withContext(ioDispatcher) {
        dataAccessObject.saveElection(election)
    }

    suspend fun deleteElection(electionId: Int) = withContext(ioDispatcher) {
        dataAccessObject.deleteElection(electionId)
    }

}