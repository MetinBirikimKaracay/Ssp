package com.example.ssp.profile

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.example.ssp.MainViewModel
import com.example.ssp.databinding.FragmentProfileBinding

class ProfileFragment : Fragment() {
    private lateinit var binding: FragmentProfileBinding
    private val mainViewModel: MainViewModel by activityViewModels()
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        handleViewOptions()

    }

    fun handleViewOptions() {
        with(binding) {
            buttonDirection.setOnClickListener {
                mainViewModel.onEditProfileFragmentButtonClick()
            }
            buttonGetProfile.setOnClickListener {
                sharedPreferences = requireActivity().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
                val savedAge = sharedPreferences.getString("age", "")
                val savedWeight = sharedPreferences.getString("weight", "")
                val savedHeight = sharedPreferences.getString("height", "")

                Log.i("ProfileFragment", "Saved age: $savedAge, saved weight: $savedWeight, saved height: $savedHeight")
            }
        }
    }
}