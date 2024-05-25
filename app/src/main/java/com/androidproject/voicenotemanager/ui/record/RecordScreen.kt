package com.androidproject.voicenotemanager.ui.record

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.androidproject.voicenotemanager.NavigationActions
import com.androidproject.voicenotemanager.ui.RecordTopBar

@Composable
fun RecordScreen(
    noteId: String?,
    noteName: String = "",
    time: String = "1:12",
    onBack: () -> Unit,
    navigationActions: NavigationActions,
    recordViewModel: RecordViewModel = hiltViewModel()
    ) {
    Scaffold(modifier = Modifier.fillMaxWidth(), floatingActionButton = {
        FloatingActionButton(onClick = {
            navigationActions.navigateToNote(noteId)
        }) {
            Icon(Icons.Filled.Edit, "")
        }
    }, topBar = {
        RecordTopBar(time, noteName, onBack)
    }) { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding)) {
            val recordUiState by recordViewModel.uiState.collectAsState()
            Text(text = recordUiState.recordedText)
        }
    }
}