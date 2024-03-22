package com.example.finalproject.presentation.notifications

import android.Manifest
import android.app.Notification
import android.app.PendingIntent
import android.content.Intent
import android.content.pm.PackageManager
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import com.example.finalproject.R
import com.example.finalproject.presentation.activity.MainActivity
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class MessagingService : FirebaseMessagingService() {

    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)
        val data = message.data
        Log.d("messaging", "Message data payload: ${message.data}")
        val symbol = data["symbol"] // Make sure the key matches what's being sent from FCM
        showNotification(title = data["title"] ?: "", desc = data["desc"] ?: "", symbol = symbol)
    }


    private fun showNotification(title: String, desc: String, symbol: String?) {
        val intent = Intent(this, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            putExtra("symbol", symbol) // Include the symbol in the intent
        }

        val pendingIntent = PendingIntent.getActivity(
            this,
            0,
            intent,
            PendingIntent.FLAG_ONE_SHOT or PendingIntent.FLAG_IMMUTABLE
        )

        val notification: Notification =
            NotificationCompat.Builder(applicationContext, "channel_id")
                .setContentTitle(title)
                .setContentText(desc)
                .setSmallIcon(R.drawable.avd_logo)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .build()

        if (ContextCompat.checkSelfPermission(
                applicationContext,
                Manifest.permission.POST_NOTIFICATIONS
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            NotificationManagerCompat.from(applicationContext)
                .notify(1, notification)
        }
    }

    override fun onNewToken(token: String) {
        super.onNewToken(token)
    }

}