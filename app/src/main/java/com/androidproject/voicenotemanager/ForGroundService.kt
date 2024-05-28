package com.androidproject.voicenotemanager

import android.app.Service
import android.content.Intent
import android.os.IBinder
import androidx.core.app.NotificationCompat
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ForegroundService @Inject constructor() : Service() {

    @Inject
    lateinit var voskApi: VOSKApi

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        when (intent?.action) {
            Actions.START.toString() -> start();
            Actions.STOP.toString() -> stop();
        }

        return super.onStartCommand(intent, flags, startId)
    }

    private fun start() {
        voskApi.pause(false)
        val notification = NotificationCompat.Builder(this, "service_channel")
        notification.setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle("Service is Running")
            .setContentText("Recording voice")
        startForeground(1, notification.build())
    }

    private fun stop() {
        voskApi.pause(true)
        stopSelf()
    }

    enum class Actions {
        START, STOP
    }
}