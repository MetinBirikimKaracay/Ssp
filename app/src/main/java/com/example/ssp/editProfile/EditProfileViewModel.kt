package com.example.ssp.editProfile

import android.content.SharedPreferences
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.ssp.R
import com.example.ssp.common.SingleLiveEvent

class EditProfileViewModel: ViewModel() {

    private val _editProfilePageViewStateLiveData: MutableLiveData<EditProfilePageViewState> = MutableLiveData()
    private val _handleLogsLiveData: SingleLiveEvent<String> = SingleLiveEvent()

    val editProfilePageViewStateLiveData: LiveData<EditProfilePageViewState> = _editProfilePageViewStateLiveData
    val handleLogsLiveData: LiveData<String> = _handleLogsLiveData


    fun getProfileData(sharedPreferences: SharedPreferences) {
        val savedAge = sharedPreferences.getString("age", "")
        val savedWeight = sharedPreferences.getString("weight", "")
        val savedHeight = sharedPreferences.getString("height", "")

        _editProfilePageViewStateLiveData.value = EditProfilePageViewState(age = savedAge, weight = savedWeight, height = savedHeight)
    }
    fun saveProfileData(age: String, weight: String, height: String, sharedPreferences: SharedPreferences): Boolean {
        if (age.isBlank() || weight.isBlank() || height.isBlank()) {
            _handleLogsLiveData.value = R.string.fill_all_fields.toString()
            return false
        }

        val editor = sharedPreferences.edit()
        editor.putString("age", age)
        editor.putString("weight", weight)
        editor.putString("height", height)
        editor.apply()

        _handleLogsLiveData.value = "Profil bilgileriniz kaydedildi."
        _editProfilePageViewStateLiveData.value = EditProfilePageViewState(age = age, weight = weight, height = height)
        return true
    }
}