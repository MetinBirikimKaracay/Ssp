package com.example.ssp.profile

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.AlertDialog
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Context.NOTIFICATION_SERVICE
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.SystemClock
import android.provider.Settings
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.example.ssp.MainViewModel
import com.example.ssp.common.channelID
import com.example.ssp.common.messageExtra
import com.example.ssp.common.notificationID
import com.example.ssp.common.titleExtra
import com.example.ssp.databinding.FragmentProfileBinding
import java.util.Calendar

class ProfileFragment : Fragment() {
    private lateinit var binding: FragmentProfileBinding
    private val mainViewModel: MainViewModel by activityViewModels()
    private val profileViewModel: ProfileViewModel by viewModels()

    private val handler = Handler(Looper.getMainLooper())
    private val notificationRunnable = object : Runnable {
        override fun run() {
            scheduleNotification()
            handler.postDelayed(this, 5000)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        handleViewOptions()
        checkNotificationPermissions(requireContext())
        with(profileViewModel) {
            profilePageViewStateLiveData.observe(viewLifecycleOwner) {
                with(binding) {
                    viewState = it
                    executePendingBindings()
                }
            }

            val sharedPreferences = requireActivity().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
            fillProfileData(sharedPreferences)
        }
    }

    private fun handleViewOptions() {
        with(binding) {
            buttonProfile.setOnClickListener {
                mainViewModel.onEditProfileFragmentButtonClick()
                createNotificationChannel()
                //checkNotificationPermissions(requireContext())
            }
        }
        mainViewModel.showAlertDialogLiveEvent.observe(viewLifecycleOwner) {
            Log.e("MainActivityLogu3", "showAlertDialogLiveEvent")
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

    private fun scheduleNotification() {
        val intent = Intent(requireContext(), com.example.ssp.common.Notification::class.java)

        val title = "Su içme Zamanı!"
        val message = "Su içmeyi unutmayın!"

        intent.putExtra(titleExtra, title)
        intent.putExtra(messageExtra, message)

        val pendingIntent = PendingIntent.getBroadcast(
            requireContext(),
            notificationID,
            intent,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )
        val alarmManager = requireContext().getSystemService(Context.ALARM_SERVICE) as AlarmManager

        val time = getTime()
        alarmManager.setExactAndAllowWhileIdle(
            AlarmManager.RTC_WAKEUP,
            time,
            pendingIntent
        )
    }
    /*private fun scheduleNotification() {
        val intent = Intent(requireContext(), com.example.ssp.common.Notification::class.java)

        val title = "Su içme Zamanı!"
        val message = "Su içmeyi unutmayın!"

        intent.putExtra(titleExtra, title)
        intent.putExtra(messageExtra, message)

        val pendingIntent = PendingIntent.getBroadcast(
            requireContext(),
            notificationID,
            intent,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )
        val alarmManager = requireContext().getSystemService(Context.ALARM_SERVICE) as AlarmManager

        val time =  1000L * 10 // 5 saniye sonra

        alarmManager.setInexactRepeating(
            AlarmManager.ELAPSED_REALTIME_WAKEUP,
            SystemClock.elapsedRealtime() + time,
            time,
            pendingIntent
        )
    }*/
    /*@SuppressLint("ShortAlarm")
    private fun scheduleNotification() {
        val intent = Intent(requireContext(), com.example.ssp.common.Notification::class.java)

        val title = "Su içme Zamanı!"
        val message = "Su içmeyi unutmayın!"

        intent.putExtra(titleExtra, title)
        intent.putExtra(messageExtra, message)

        val pendingIntent = PendingIntent.getBroadcast(
            requireContext(),
            notificationID,
            intent,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )
        val alarmManager = requireContext().getSystemService(Context.ALARM_SERVICE) as AlarmManager

        val time = 1000L * 10

        alarmManager.setRepeating(
            AlarmManager.ELAPSED_REALTIME_WAKEUP,
            time,//SystemClock.elapsedRealtime() + time,
            time,
            pendingIntent
        )
    }*/

    private fun getTime(): Long {
        val calendar = Calendar.getInstance()
        calendar.add(Calendar.SECOND, 5)
        return calendar.timeInMillis
    }

    private fun createNotificationChannel() {
        val name = "Notify Channel"
        val desc = "A Description of the Channel"
        val importance = NotificationManager.IMPORTANCE_DEFAULT
        val channel = NotificationChannel(channelID, name, importance)
        channel.description = desc

        val notificationManager = requireContext().getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)

        scheduleNotification()
    }

    private fun checkNotificationPermissions(context: Context): Boolean {
        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val isEnabled = notificationManager.areNotificationsEnabled()
        if (!isEnabled) {
            requestNotificationPermission(context)
            return false
        }
        return true
    }

    private fun requestNotificationPermission(context: Context) {
        val builder = AlertDialog.Builder(context)
        builder.setTitle("Bildirim İzni")
        builder.setMessage("Su içmeniz gerektiğini hatırlatabilmemiz için bildirim almak ister misiniz?")
        builder.setPositiveButton("İzin Ver") { _, _ ->
            openNotificationSettings(context)
        }
        builder.setNegativeButton("İptal") { dialog, _ ->
            dialog.dismiss()
        }
        builder.create().show()
    }

    private fun openNotificationSettings(context: Context) {
        val intent = Intent(Settings.ACTION_APP_NOTIFICATION_SETTINGS)
        intent.putExtra(Settings.EXTRA_APP_PACKAGE, context.packageName)
        context.startActivity(intent)
    }
}