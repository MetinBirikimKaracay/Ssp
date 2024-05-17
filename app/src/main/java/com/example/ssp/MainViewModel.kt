package com.example.ssp

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.ssp.common.navigation.NavigationData
import com.example.ssp.common.SingleLiveEvent

class MainViewModel: ViewModel() {

    private val _navigateToDestinationComponentSingleLiveEvent: SingleLiveEvent<NavigationData> = SingleLiveEvent()
    val navigateToDestinationComponentSingleLiveEvent: LiveData<NavigationData> = _navigateToDestinationComponentSingleLiveEvent

    fun onEditProfileFragmentButtonClick() {
        _navigateToDestinationComponentSingleLiveEvent.value = NavigationData(R.id.editProfileFragment)
    }

    fun onProfileFragmentButtonClick() {
        _navigateToDestinationComponentSingleLiveEvent.value = NavigationData(R.id.profileFragment)
    }
}