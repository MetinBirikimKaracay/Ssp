package com.example.ssp.profile

import android.content.Context
import android.content.SharedPreferences
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ProfileViewModel : ViewModel() {

    private val _profilePageViewStateLiveData: MutableLiveData<ProfilePageViewState> = MutableLiveData()
    val profilePageViewStateLiveData: LiveData<ProfilePageViewState> = _profilePageViewStateLiveData

    fun fillProfileData(sharedPreferences: SharedPreferences) {
        val savedAge = sharedPreferences.getString("age", "")
        val savedWeight = sharedPreferences.getString("weight", "")
        val savedHeight = sharedPreferences.getString("height", "")

        _profilePageViewStateLiveData.value = ProfilePageViewState(age = savedAge, weight = savedWeight, height = savedHeight)
    }
}