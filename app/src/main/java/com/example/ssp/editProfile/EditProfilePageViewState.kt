package com.example.ssp.editProfile

import android.content.Context
import androidx.core.content.ContextCompat
import com.example.ssp.R

data class EditProfilePageViewState(
    val age: String? = null,
    val weight: String? = null,
    val height: String? = null
) {
    fun getUserAge(): String = age ?: "25"
    fun getUserWeight(): String = weight ?: "70"
    fun getUserHeight(): String = height ?: "180"
    fun buttonText(context: Context): String = ContextCompat.getString(context, R.string.button_save)
}