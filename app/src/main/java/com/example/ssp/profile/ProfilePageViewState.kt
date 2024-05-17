package com.example.ssp.profile

import android.content.Context
import android.view.View
import androidx.core.content.ContextCompat
import com.example.ssp.R

data class ProfilePageViewState(
    val age: String? = null,
    val weight: String? = null,
    val height: String? = null,
    val weather: String? = null,
) {
    fun profileDatasVisibility(): Int = if (age.isNullOrBlank()) View.GONE else View.VISIBLE
    fun getUserAge(): String = age ?: "25"
    fun getUserWeight(): String = (weight ?: "70") +" kg"
    fun getUserHeight(): String = (height ?: "180") +" cm"
    fun getWeatherState(): String = weather ?: "Sıcak"
    fun buttonText(context: Context): String = if (age.isNullOrBlank()) ContextCompat.getString(context, R.string.fill_profile) else ContextCompat.getString(context, R.string.update_profile)
}