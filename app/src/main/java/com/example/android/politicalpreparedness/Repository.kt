package com.example.android.politicalpreparedness

import androidx.lifecycle.LiveData
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
        private val dataAccessObject: ElectionDao,
        private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) {
    val elections: LiveData<List<Election>> = dataAccessObject.getElections()

    suspend fun refreshElections() = withContext(ioDispatcher) {
        try {
            val response = CivicsApi
                    .retrofitService
                    .getElectionsAsync()
                    .await()

            val elections = parseElectionsJsonResult(JSONObject(response))
            dataAccessObject.cacheElections(*elections.toTypedArray())
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
                val electionDay = dateFormat.parse(electionJson.getString("electionDay")) ?: continue
                val division = ElectionAdapter().divisionFromJson(electionJson.getString("ocdDivisionId"))

                val election = Election(
                        electionJson.getInt("id"),
                        electionJson.getString("name"),
                        electionDay,
                        division
                )

                electionsList.add(election)
            }
        }

        return electionsList
    }

}