package com.example.android.politicalpreparedness

import android.app.Application
import com.example.android.politicalpreparedness.database.ElectionDatabase
import com.example.android.politicalpreparedness.election.ElectionsViewModel
import com.example.android.politicalpreparedness.election.VoterInfoViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.dsl.module
import timber.log.Timber

class AppDelegate : Application() {

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())

        val koinModule = module {
            viewModel { ElectionsViewModel(get(), get()) }
            viewModel { VoterInfoViewModel(get(), get()) }
            single { Repository(get()) }
            single { ElectionDatabase.getInstance(this@AppDelegate).electionDao }
        }

        startKoin {
            androidContext(this@AppDelegate)
            modules(listOf(koinModule))
        }
    }

}