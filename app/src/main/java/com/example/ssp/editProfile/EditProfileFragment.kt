package com.example.ssp.editProfile

import android.app.AlertDialog
import android.app.TimePickerDialog
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import androidx.fragment.app.Fragment
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.example.ssp.MainViewModel
import com.example.ssp.R
import com.example.ssp.databinding.FragmentEditProfileBinding
import java.util.Calendar

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

        val weathers: MutableList<String> = mutableListOf()
        weathers.add("Sıcak")
        weathers.add("Ilık")
        weathers.add("Soğuk")
        val arrayAdapterWeather = ArrayAdapter(requireContext(), R.layout.item_height_dropdown, weathers)
        binding.weatherDropdownMenu.setAdapter(arrayAdapterWeather)
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
        mainViewModel.showAlertDialogLiveEvent.observe(viewLifecycleOwner) {
            Log.e("MainActivityLogu4", "showAlertDialogLiveEvent")
            showNotificationDialog()
        }
    }

    private fun showNotificationDialog() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Bildirim İzni")
        builder.setMessage("Bir sonraki çark çevirme işleminizde bildirim almak ister misiniz?")
        builder.setPositiveButton("İzin Ver") { _, _ ->
        }
        builder.create().show()
    }

    private fun handleViewOptions() {
        with(binding) {
            buttonSave.setOnClickListener {
                val age = binding.editTextAge.text.toString()
                val weight = binding.editTextWeight.text.toString()
                val height = binding.heightDropdownMenu.text.toString()
                val weather = binding.weatherDropdownMenu.text.toString()
                val gender = binding.radioGroupGender.checkedRadioButtonId
                val gender2 = when (binding.radioGroupGender.checkedRadioButtonId) {
                    R.id.radioButtonMale -> "Erkek"
                    R.id.radioButtonFemale -> "Kadın"
                    else -> ""
                }

                val result = editProfileViewModel.saveProfileData(age, weight, height, weather, gender, sharedPreferences)
                if (result) {
                    mainViewModel.onProfileFragmentButtonClick()
                }
            }
            heightDropdownMenu.setOnClickListener {
                closeKeyboard(it)
            }
            buttonSelectBreakfastTime.setOnClickListener {
                showTimePickerDialog { pickedTime ->
                    Log.e("Picked_Time", pickedTime)
                }
            }
            buttonSelectLunchTime.setOnClickListener {
                showTimePickerDialog { pickedTime ->
                    Log.e("Picked_Time", pickedTime)
                }
            }
            buttonSelectDinnerTime.setOnClickListener {
                showTimePickerDialog { pickedTime ->
                    Log.e("Picked_Time", pickedTime)
                }
            }
            buttonSelectSleepTime.setOnClickListener {
                showTimePickerDialog { pickedTime ->
                    Log.e("Picked_Time", pickedTime)
                }
            }
            buttonSelectWakeTime.setOnClickListener {
                showTimePickerDialog { pickedTime ->
                    Log.e("Picked_Time", pickedTime)
                }
            }
        }
    }

    private fun showTimePickerDialog(onTimeSet: (String) -> Unit) {
        val currentTime = Calendar.getInstance()
        val startHour = currentTime.get(Calendar.HOUR_OF_DAY)
        val startMinute = currentTime.get(Calendar.MINUTE)
        TimePickerDialog(requireContext(), { _, hourOfDay, minute ->
            val time = "$hourOfDay:$minute"
            onTimeSet(time)
        }, startHour, startMinute, true).show()
    }

    private fun closeKeyboard(view: View) {
        val imm = activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }
}
