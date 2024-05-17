package com.example.ssp.editProfile

import android.content.Context
import androidx.core.content.ContextCompat
import com.example.ssp.R

data class EditProfilePageViewState(
    val age: String? = null,
    val weight: String? = null,
    val height: String? = null,
    val weather: String? = null,
    val gender: Int? = null
) {
    fun maleIsSelected(): Boolean = R.id.radioButtonMale == gender
    fun femaleIsSelected(): Boolean = R.id.radioButtonFemale == gender
    fun getUserAge(): String = age ?: "25"
    fun getUserWeight(): String = weight ?: "70"
    fun getUserHeight(): String = height ?: "180"
    fun getUserWeather(): String = weather ?: "Sıcak"
    fun buttonText(context: Context): String = ContextCompat.getString(context, R.string.button_save)
}