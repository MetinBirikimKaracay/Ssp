package com.example.ssp.editProfile

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import androidx.fragment.app.Fragment
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import com.example.ssp.R
import com.example.ssp.databinding.FragmentEditProfileBinding

class EditProfileFragment : Fragment() {

    private lateinit var binding: FragmentEditProfileBinding
    private lateinit var sharedPreferences: SharedPreferences

    override fun onResume() {
        super.onResume()
        val heights: MutableList<String> = mutableListOf()
        for (i in 140..210) {
            heights.add(i.toString())
        }
        val arrayAdapter = ArrayAdapter(requireContext(), R.layout.item_height_dropdown, heights)
        binding.height.setAdapter(arrayAdapter)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentEditProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        handleViewOptions()
        sharedPreferences = requireActivity().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
    }

    fun handleViewOptions() {
        with(binding) {
            buttonSave.setOnClickListener {
                saveProfileData()
            }
        }
    }
    private fun saveProfileData() {
        val age = binding.editTextAge.text.toString()
        val weight = binding.editTextWeight.text.toString()
        val height = binding.height.text.toString()

        val editor = sharedPreferences.edit()
        editor.putString("age", age)
        editor.putString("weight", weight)
        editor.putString("height", height)
        editor.apply()

        // Kullanıcıya bilginin kaydedildiği bildirimi gösterilebilir
        Toast.makeText(requireContext(), "Bilgiler kaydedildi.", Toast.LENGTH_SHORT).show()
    }

}
