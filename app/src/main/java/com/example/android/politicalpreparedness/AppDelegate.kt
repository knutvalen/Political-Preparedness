package com.example.android.politicalpreparedness

import android.app.Application
import com.example.android.politicalpreparedness.election.ElectionsViewModel
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
            viewModel {
                ElectionsViewModel(get())
            }
        }

        startKoin {
            androidContext(this@AppDelegate)
            modules(listOf(koinModule))
        }
    }

}