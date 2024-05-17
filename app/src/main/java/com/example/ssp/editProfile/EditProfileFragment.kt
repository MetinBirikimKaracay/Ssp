package com.example.ssp.editProfile

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import androidx.fragment.app.Fragment
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.example.ssp.MainViewModel
import com.example.ssp.R
import com.example.ssp.databinding.FragmentEditProfileBinding

class EditProfileFragment : Fragment() {

    private lateinit var binding: FragmentEditProfileBinding
    private val editProfileViewModel: EditProfileViewModel by viewModels()
    private val mainViewModel: MainViewModel by activityViewModels()
    private lateinit var sharedPreferences: SharedPreferences

    override fun onResume() {
        super.onResume()
        val heights: MutableList<String> = mutableListOf()
        for (i in 140..210) {
            heights.add(i.toString())
        }
        val arrayAdapter = ArrayAdapter(requireContext(), R.layout.item_height_dropdown, heights)
        binding.heightDropdownMenu.setAdapter(arrayAdapter)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentEditProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        handleViewOptions()
        sharedPreferences = requireActivity().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        with(editProfileViewModel) {
            editProfilePageViewStateLiveData.observe(viewLifecycleOwner) {
                with(binding) {
                    viewState = it
                    executePendingBindings()
                }
            }

            handleLogsLiveData.observe(viewLifecycleOwner) {
                Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
            }
            getProfileData(sharedPreferences)
        }
    }

    private fun handleViewOptions() {
        with(binding) {
            buttonSave.setOnClickListener {
                val age = binding.editTextAge.text.toString()
                val weight = binding.editTextWeight.text.toString()
                val height = binding.heightDropdownMenu.text.toString()

                val result = editProfileViewModel.saveProfileData(age, weight, height, sharedPreferences)
                if (result) {
                    mainViewModel.onProfileFragmentButtonClick()
                }
            }
            heightDropdownMenu.setOnClickListener {
                closeKeyboard(it)
            }
        }
    }

    private fun closeKeyboard(view: View) {
        val imm = activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }
}
