@file:OptIn(ExperimentalMaterial3Api::class)

package com.androidproject.voicenotemanager.ui

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Save
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.rounded.Mic
import androidx.compose.material.icons.rounded.Share
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.androidproject.voicenotemanager.R
import com.androidproject.voicenotemanager.ui.note.NoteViewModel
import com.androidproject.voicenotemanager.ui.record.RecordViewModel
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionStatus
import com.google.accompanist.permissions.rememberPermissionState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun CategoryListTopBar(openDrawer: () -> Unit) {
    TopAppBar(
        title = {
            Text(
                modifier = Modifier.padding(start = 5.dp),
                text = stringResource(id = R.string.app_name),
                style = MaterialTheme.typography.titleLarge
            )
        },
        navigationIcon = {
            IconButton(onClick = openDrawer) {
                Icon(Icons.Filled.Menu, "")
            }
        },
        modifier = Modifier.fillMaxWidth()
    )
}

@Composable
fun NoteListTopBar(
    categoryName: String,
    onBack: () -> Unit
) {
    TopAppBar(
        title = {
            Text(
                modifier = Modifier.padding(start = 5.dp),
                text = categoryName,
                style = MaterialTheme.typography.titleLarge
            )
        },
        navigationIcon = {
            IconButton(onClick = onBack) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, "")
            }
        },
        modifier = Modifier.fillMaxWidth()
    )
}

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun RecordTopBar(
    time: Long,
    noteName: String,
    onBack: () -> Unit,
    recordViewModel: RecordViewModel
) {

    TopAppBar(
        title = {
            Text(
                modifier = Modifier.padding(start = 5.dp),
                text = noteName,
                style = MaterialTheme.typography.titleLarge
            )
        },
        navigationIcon = {
            IconButton(onClick = {
                recordViewModel.stopRecording()
                recordViewModel.voskApi.mainText = ""
                onBack()
            }) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, "")
            }
        },
        actions = {
            IconButton(onClick = {
                recordViewModel.share()
            }) {
                Icon(
                    modifier = Modifier.size(30.dp),
                    imageVector = Icons.Rounded.Share,
                    contentDescription = ""
                )
            }
            val recordPermissionState = rememberPermissionState(
                android.Manifest.permission.RECORD_AUDIO
            )
            IconButton(onClick = {
                when (recordPermissionState.status) {
                    PermissionStatus.Granted -> {
                        recordViewModel.startStopRecording()
                    }

                    is PermissionStatus.Denied -> {
                        recordPermissionState.launchPermissionRequest()
                    }
                }

            }) {
                Icon(
                    modifier = Modifier.size(30.dp),
                    imageVector = Icons.Rounded.Mic,
                    contentDescription = ""
                )
            }
            Text(
                text = formatTime(time),
                modifier = Modifier.padding(start = 5.dp),
            )
        },
        modifier = Modifier.fillMaxWidth(),
    )
}

fun formatTime(millis: Long): String {
    val minutes = millis / 60L
    val seconds = millis % 60L
    return if (seconds < 10)
        "$minutes:0$seconds"
    else
        "$minutes:$seconds"
}

@Composable
fun NoteTopBar(
    noteName: String,
    onBack: () -> Unit,
    saveNote: () -> Unit,
    snackbarHostState: SnackbarHostState,
    noteViewModel: NoteViewModel
) {
    TopAppBar(
        title = {
            Text(
                modifier = Modifier.padding(start = 5.dp),
                text = noteName,
                style = MaterialTheme.typography.titleLarge
            )
        },
        navigationIcon = {
            IconButton(onClick = onBack) {
                Icon(Icons.Filled.ArrowBack, "")
            }
        },
        actions = {
            IconButton(onClick = {
                noteViewModel.share()
            }) {
                Icon(Icons.Filled.Share, "Save")
            }
            val coroutineScope: CoroutineScope = rememberCoroutineScope()
            IconButton(onClick = {
                coroutineScope.launch {
                    snackbarHostState.showSnackbar("saved!")
                }
                saveNote()
            }) {
                Icon(Icons.Filled.Save, "Save")
            }
        },
        modifier = Modifier.fillMaxWidth()
    )
}