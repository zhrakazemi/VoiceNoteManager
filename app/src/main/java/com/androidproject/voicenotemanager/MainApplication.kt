package com.androidproject.voicenotemanager

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import dagger.hilt.android.HiltAndroidApp

/**
 * The main application class for the Voice Note Manager application.
 *
 * This class leverages the `@HiltAndroidApp` annotation to configure Hilt for dependency injection
 * throughout the application. It also initializes a notification channel for API level 26 (Android O)
 * and above, ensuring proper notification behavior.
 */
@HiltAndroidApp
class MainApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            /**
             * Create a notification channel for API level 26 (Android O) and above.
             * This ensures proper notification behavior, including customization options
             * for users in the system settings.
             */
            val channel = NotificationChannel(
                "service_channel",
                "My Notification",
                NotificationManager.IMPORTANCE_HIGH
            )

            val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)

        }
    }
}
