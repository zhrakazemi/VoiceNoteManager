package com.androidproject.voicenotemanager.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.androidproject.voicenotemanager.NavGraph
import com.androidproject.voicenotemanager.ui.theme.VoiceNoteManagerTheme
import dagger.hilt.android.AndroidEntryPoint

/**
 * The main activity of the application.
 *
 * This activity serves as the entry point and utilizes Jetpack Compose for the UI.
 * It leverages the @AndroidEntryPoint annotation for dependency injection using Hilt.
 */
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            VoiceNoteManagerTheme {
                NavGraph()
            }
        }
    }
}
