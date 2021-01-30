package com.example.android.politicalpreparedness

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.android.politicalpreparedness.database.ElectionDao
import com.example.android.politicalpreparedness.network.CivicsApi
import com.example.android.politicalpreparedness.network.jsonadapter.ElectionAdapter
import com.example.android.politicalpreparedness.network.models.Election
import kotlinx.coroutines.*
import org.json.JSONObject
import retrofit2.await
import timber.log.Timber
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class Repository(
    dataAccessObject: ElectionDao,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) {
    private val _upcomingElections = MutableLiveData<List<Election>>()
    val upcomingElections: LiveData<List<Election>>
        get() = _upcomingElections

    val savedElections: LiveData<List<Election>> = dataAccessObject.getElections()

    suspend fun refreshElections() = withContext(ioDispatcher) {
        try {
            val response = CivicsApi
                .retrofitService
                .getElectionsAsync()
                .await()

            val elections = parseElectionsJsonResult(JSONObject(response))

            withContext(Dispatchers.Main) {
                _upcomingElections.value = elections
            }
//            dataAccessObject.cacheElections(*elections.toTypedArray())
        } catch (e: Exception) {
            Timber.e(e)
        }
    }

    private fun parseElectionsJsonResult(jsonResult: JSONObject): ArrayList<Election> {
        val electionsList = ArrayList<Election>()

        if (jsonResult.has("elections")) {
            val elections = jsonResult.getJSONArray("elections")
            val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())

            for (i in 0 until elections.length()) {
                val electionJson = elections.getJSONObject(i)
                val id = electionJson.getInt("id")
                val name = electionJson.getString("name")
                val electionDay = dateFormat.parse(electionJson.getString("electionDay"))
                    ?: continue
                val division = ElectionAdapter().divisionFromJson(electionJson.getString("ocdDivisionId"))
                electionsList.add(Election(id, name, electionDay, division))
            }
        }

        return electionsList
    }

}