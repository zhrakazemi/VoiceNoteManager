package com.androidproject.voicenotemanager

import android.app.Service
import android.content.Intent
import android.os.IBinder
import androidx.core.app.NotificationCompat
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

/**
 * Foreground service for background voice recording functionality.
 *
 * This service leverages Hilt for dependency injection and utilizes a notification
 * to indicate its running state. It interacts with a `VOSKApi` instance (not shown)
 * for voice recording and can be started or stopped through intents with specific actions.
 */
@AndroidEntryPoint
class ForegroundService @Inject constructor() : Service() {

    /**
     * Instance of the VOSKApi for voice recognition functionality, injected by Hilt.
     */
    @Inject
    lateinit var voskApi: VOSKApi

    /**
     * This service does not provide any binding functionality.
     *
     * @param intent The intent used to bind to the service.
     * @return null, indicating this service doesn't support binding.
     */
    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    /**
     * Handles start commands received by the service.
     *
     * @param intent The intent containing the start command information.
     * @param flags Additional flags associated with the start command.
     * @param startId A unique identifier for this specific service start.
     * @return The return value indicating how to handle subsequent calls to onStartCommand().
     */
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        when (intent?.action) {
            Actions.START.toString() -> start();
            Actions.STOP.toString() -> stop();
        }

        return super.onStartCommand(intent, flags, startId)
    }

    /**
     * Starts recording by resuming the VOSKApi and shows a foreground service notification.
     */
    private fun start() {
        voskApi.pause(false)
        val notification = NotificationCompat.Builder(this, "service_channel")
        notification.setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle("Service is Running")
            .setContentText("Recording voice")
        startForeground(1, notification.build())
    }

    /**
     * Stops recording by pausing the VOSKApi and stops the service.
     */
    private fun stop() {
        voskApi.pause(true)
        stopSelf()
    }


    /**
     * Enum defining the actions supported by the service through intents.
     */
    enum class Actions {
        START, STOP
    }
}