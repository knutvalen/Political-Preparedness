package com.example.android.politicalpreparedness

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.android.politicalpreparedness.database.ElectionDao
import com.example.android.politicalpreparedness.network.CivicsApi
import com.example.android.politicalpreparedness.network.models.*
import com.example.android.politicalpreparedness.representative.model.Representative
import kotlinx.coroutines.*
import retrofit2.HttpException
import timber.log.Timber

class Repository(
    private val dataAccessObject: ElectionDao,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) {
    private val _upcomingElections = MutableLiveData<List<Election>>()
    val upcomingElections: LiveData<List<Election>>
        get() = _upcomingElections

    val savedElections: LiveData<List<Election>> = dataAccessObject.getElections()

    private val _voterinfo = MutableLiveData<VoterInfoResponse?>()
    val voterinfo: LiveData<VoterInfoResponse?>
        get() = _voterinfo

    private val _apiError = MutableLiveData<String?>()
    val apiError: LiveData<String?>
        get() = _apiError

    private val _representatives = MutableLiveData<List<Representative>?>()
    val representatives: LiveData<List<Representative>?>
        get() = _representatives

    suspend fun refreshUpcomingElections() = withContext(ioDispatcher) {
        try {
            val response = CivicsApi
                .retrofitService
                .getElectionsAsync()
                .await()

            val elections = response.elections

            withContext(Dispatchers.Main) {
                _upcomingElections.value = elections
            }
        } catch (e: HttpException) {
            @Suppress("BlockingMethodInNonBlockingContext")
            val errorBody = e.response()?.errorBody()?.string()
            Timber.e(errorBody)

            withContext(Dispatchers.Main) {
                _apiError.value = errorBody
            }
        }
    }

    suspend fun refreshVoterinfo(electionId: Int, address: String) = withContext(ioDispatcher) {
        Timber.d("electionId: $electionId, address: $address")
        try {
            val response = CivicsApi
                .retrofitService
                .getVoterinfoAsync(electionId, address)
                .await()

            withContext(Dispatchers.Main) {
                _voterinfo.value = response
            }
        } catch (e: HttpException) {
            @Suppress("BlockingMethodInNonBlockingContext")
            val errorBody = e.response()?.errorBody()?.string()
            Timber.e(errorBody)

            withContext(Dispatchers.Main) {
                _apiError.value = errorBody
            }
        }
    }

    suspend fun refreshRepresentatives(address: String) = withContext(ioDispatcher) {
        Timber.d("address: $address")

        try {
            val response = CivicsApi.retrofitService.getRepresentativesAsync(address).await()
            val representatives = response.asRepresentatives()
            Timber.d("representatives: $representatives")

            withContext(Dispatchers.Main) {
                _representatives.value = representatives
            }
        } catch (e: HttpException) {
            @Suppress("BlockingMethodInNonBlockingContext")
            val errorBody = e.response()?.errorBody()?.string()
            Timber.e(errorBody)

            withContext(Dispatchers.Main) {
                _apiError.value = errorBody
            }
        }
    }

    suspend fun saveElection(election: Election) = withContext(ioDispatcher) {
        dataAccessObject.saveElection(election)
    }

    suspend fun deleteElection(electionId: Int) = withContext(ioDispatcher) {
        dataAccessObject.deleteElection(electionId)
    }

    fun resetErrorMessage() {
        _apiError.value = null
    }

    fun destroyVoterinfo() {
        _voterinfo.value = null
    }

    fun destroyRepresentatives() {
        _representatives.value = null
    }

}