package com.example.android.politicalpreparedness.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.android.politicalpreparedness.network.models.Election

@Dao
interface ElectionDao {

    @Query("DELETE FROM election_table WHERE id = :id")
    fun deleteElection(id: Int)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveElection(election: Election)

    @Query("SELECT * FROM election_table ORDER BY electionDay ASC")
    fun getElections(): LiveData<List<Election>>

}