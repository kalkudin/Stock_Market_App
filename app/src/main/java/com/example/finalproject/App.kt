package com.example.finalproject

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class App : Application(){

    override fun onCreate() {
        super.onCreate()
        createChannels()
    }

    private fun createChannels() {

        val channel = NotificationChannel(
            "channel_id",
            "Notification",
            NotificationManager.IMPORTANCE_HIGH
        )

        val manager: NotificationManager = applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        manager.createNotificationChannel(channel)
    }
}