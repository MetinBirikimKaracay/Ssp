package com.example.ssp.common

import android.app.ActivityManager
import android.app.AlertDialog
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import com.example.ssp.MainViewModel
import com.example.ssp.R

const val notificationID = 121
const val channelID = "channel1"
const val titleExtra = "titleExtra"
const val messageExtra = "messageExtra"

class Notification : BroadcastReceiver() {
    private var mainViewModel = MainViewModel()
    override fun onReceive(context: Context, intent: Intent) {
        if (isAppInBackground(context)) {
            val packageName = context.packageName
            val className = "${packageName}.MainActivity"

            val tapIntent = Intent().apply {
                setClassName(packageName, className)
                action = Intent.ACTION_MAIN
                addCategory(Intent.CATEGORY_LAUNCHER)
                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
                putExtra("extra_key", "Rewarded Wheel")
            }

            val pendingIntent = PendingIntent.getActivity(context, 0, tapIntent, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE)

            val notification = NotificationCompat.Builder(context, channelID)
                .setSmallIcon(R.mipmap.ic_launcher_round)
                .setContentTitle(intent.getStringExtra(titleExtra))
                .setContentText(intent.getStringExtra(messageExtra))
                .setContentIntent(pendingIntent)
                .setColor(ContextCompat.getColor(context, R.color.white))
                .setAutoCancel(true)
                .build()

            val manager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            manager.notify(notificationID, notification)
        } else {
            Log.e("MainActivityLogu2", "showAlertDialogLiveEvent")
            mainViewModel.showDialog()
            //showNotificationDialog(context, intent)
        }
    }

    private fun isAppInBackground(context: Context): Boolean {
        val activityManager = context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        val packageName = context.packageName
        val appProcesses = activityManager.runningAppProcesses ?: return false
        for (appProcess in appProcesses) {
            if (appProcess.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND && appProcess.processName == packageName) {
                return false
            }
        }
        return true
    }

    private fun showNotificationDialog(context: Context, intent: Intent) {
        val dialogBuilder = AlertDialog.Builder(context)
        dialogBuilder.setMessage(intent.getStringExtra(messageExtra))
            .setCancelable(false)
            .setPositiveButton("Tamam") { dialog, _ ->
                dialog.dismiss()
            }
        val alertDialog = dialogBuilder.create()
        alertDialog.show()
    }
}