package com.androidproject.voicenotemanager.ui.record

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.androidproject.voicenotemanager.ui.RecordTopBar

@Composable
fun RecordScreen(
    noteId: String?,
    noteName: String = "",
    time: String = "1:12"
) {
    Scaffold(modifier = Modifier.fillMaxWidth(), topBar = {
        RecordTopBar(time, noteName)
    }) { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding)) {

        }
    }
}

@Preview
@Composable
private fun PreviewRecord() {
    RecordScreen(noteId = "", noteName = "math")
}