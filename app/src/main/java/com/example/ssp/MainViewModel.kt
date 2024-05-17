package com.example.ssp

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.ssp.common.navigation.NavigationData
import com.example.ssp.common.SingleLiveEvent

class MainViewModel: ViewModel() {

    private val _navigateToDestinationComponentSingleLiveEvent: SingleLiveEvent<NavigationData> = SingleLiveEvent()
    private val _showAlertDialogLiveEvent: SingleLiveEvent<Any> = SingleLiveEvent()

    val navigateToDestinationComponentSingleLiveEvent: LiveData<NavigationData> = _navigateToDestinationComponentSingleLiveEvent
    val showAlertDialogLiveEvent: LiveData<Any> = _showAlertDialogLiveEvent

    fun onEditProfileFragmentButtonClick() {
        _navigateToDestinationComponentSingleLiveEvent.value = NavigationData(R.id.editProfileFragment)
    }

    fun onProfileFragmentButtonClick() {
        _navigateToDestinationComponentSingleLiveEvent.value = NavigationData(R.id.profileFragment)
    }

    fun showDialog() {
        Log.e("MainActivityLogu1", "showAlertDialogLiveEvent")
        _showAlertDialogLiveEvent.call()
    }
}