package com.example.android.politicalpreparedness.representative

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.android.politicalpreparedness.Repository
import com.example.android.politicalpreparedness.network.models.Address
import kotlinx.coroutines.launch
import timber.log.Timber

class RepresentativeViewModel(
    application: Application,
    private val repository: Repository
) : AndroidViewModel(application) {

    val representatives = repository.representatives
    private val address = MutableLiveData<Address?>()
    val addressLine1 = MutableLiveData<String>()
    val addressLine2 = MutableLiveData<String>()
    val city = MutableLiveData<String>()
    val state = MutableLiveData<String>()
    val zip = MutableLiveData<String>()

    fun refreshRepresentatives(address: Address) {
        Timber.d("address: $address")
        this.address.value = address
        addressLine1.value = address.line1
        address.line2.let { addressLine2.value = it }
        city.value = address.city
        state.value = address.state
        zip.value = address.zip

        viewModelScope.launch {
            repository.refreshRepresentatives(address.toFormattedString())
        }
    }

    fun refreshRepresentatives() {
        val addressLine1 = addressLine1.value ?: return
        val city = city.value ?: return
        val state = state.value ?: return
        val zip = zip.value ?: return

        val address = Address(addressLine1, addressLine2.value, city, state, zip)
        refreshRepresentatives(address)
    }

    //TODO: Establish live data for representatives and address

    //TODO: Create function to fetch representatives from API from a provided address

    /**
     *  The following code will prove helpful in constructing a representative from the API. This code combines the two nodes of the RepresentativeResponse into a single official :

    val (offices, officials) = getRepresentativesDeferred.await()
    _representatives.value = offices.flatMap { office -> office.getRepresentatives(officials) }

    Note: getRepresentatives in the above code represents the method used to fetch data from the API
    Note: _representatives in the above code represents the established mutable live data housing representatives

     */

    //TODO: Create function get address from geo location

    //TODO: Create function to get address from individual fields

}
