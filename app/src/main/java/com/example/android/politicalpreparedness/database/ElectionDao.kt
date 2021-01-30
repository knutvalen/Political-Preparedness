package com.example.android.politicalpreparedness.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.android.politicalpreparedness.network.models.Election

@Dao
interface ElectionDao {

    //TODO: Add select single election query

    //TODO: Add delete query

    //TODO: Add clear query

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun cacheElections(vararg elections: Election)

    @Query("SELECT * FROM election_table ORDER BY electionDay ASC")
    fun getElections(): LiveData<List<Election>>

}