package com.androidproject.voicenotemanager.ui.record

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.androidproject.voicenotemanager.NavigationActions
import com.androidproject.voicenotemanager.ui.RecordTopBar

@Composable
fun RecordScreen(
    noteId: String?,
    onBack: () -> Unit,
    navigationActions: NavigationActions,
    recordViewModel: RecordViewModel = hiltViewModel()
) {
    val recordUiState by recordViewModel.uiState.collectAsState()
    Scaffold(modifier = Modifier.fillMaxWidth(), floatingActionButton = {
        FloatingActionButton(onClick = {
            navigationActions.navigateToNote(noteId)
        }) {
            Icon(Icons.Filled.Edit, "")
        }
    }, topBar = {
        RecordTopBar(
            recordUiState.timer, recordUiState.noteName, onBack, recordViewModel
        )
    }) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxWidth()
                .verticalScroll(rememberScrollState())
        ) {
            Text(
                modifier = Modifier.padding(10.dp),
                textAlign = TextAlign.Start,
                text = recordUiState.recordedText,
                style = TextStyle(textDirection = TextDirection.Rtl),
                fontSize = 20.sp
            )
        }
    }
}