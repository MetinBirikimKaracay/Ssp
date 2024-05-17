package com.example.ssp.editProfile

import android.content.SharedPreferences
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
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
        val savedWeather = sharedPreferences.getString("weather", "")
        val savedGender = sharedPreferences.getInt("gender", 0)

        _editProfilePageViewStateLiveData.value = EditProfilePageViewState(age = savedAge, weight = savedWeight, height = savedHeight, weather = savedWeather, gender = savedGender)
    }
    fun saveProfileData(age: String, weight: String, height: String, weather: String, gender: Int, sharedPreferences: SharedPreferences): Boolean {
        if (age.isBlank() || weight.isBlank() || height.isBlank() || weather.isBlank()) {
            _handleLogsLiveData.value = "Lütfen tüm alanları doldurunuz"
            return false
        }

        val editor = sharedPreferences.edit()
        editor.putString("age", age)
        editor.putString("weight", weight)
        editor.putString("height", height)
        editor.putString("weather", weather)
        editor.putInt("gender", gender)
        editor.apply()

        _handleLogsLiveData.value = "Bilgileriniz Kaydedildi"
        return true
    }
}