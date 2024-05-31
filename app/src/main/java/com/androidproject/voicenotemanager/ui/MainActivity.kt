package com.androidproject.voicenotemanager.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.androidproject.voicenotemanager.NavGraph
import com.androidproject.voicenotemanager.ui.theme.VoiceNoteManagerTheme
import dagger.hilt.android.AndroidEntryPoint

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
