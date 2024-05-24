package com.androidproject.voicenotemanager.ui.record

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.androidproject.voicenotemanager.ui.RecordTopBar

@Composable
fun RecordScreen(
    noteId: String?,
    noteName: String = "",
    time: String = "1:12",
    onBack: () -> Unit,

) {
    Scaffold(modifier = Modifier.fillMaxWidth(), topBar = {
        RecordTopBar(time, noteName, onBack)
    }) { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding)) {

        }
    }
}