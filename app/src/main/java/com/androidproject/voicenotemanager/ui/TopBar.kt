@file:OptIn(ExperimentalMaterial3Api::class)

package com.androidproject.voicenotemanager.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.rounded.Menu
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.androidproject.voicenotemanager.R

@Composable
fun CategoryListTopBar(
) {
    TopAppBar(
        title = {
            Text(
                modifier = Modifier.padding(start = 5.dp),
                text = stringResource(id = R.string.app_name),
                style = MaterialTheme.typography.titleLarge
            )
        },
        navigationIcon = {
            IconButton(onClick = {}) {
                Icon(Icons.Filled.Menu, "")
            }
        },
        modifier = Modifier.fillMaxWidth()
    )
}

@Composable
fun NoteListTopBar(
    categoryName: String,
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
            IconButton(onClick = {}) {
                Icon(Icons.Filled.Menu, "")
            }
        },
        modifier = Modifier.fillMaxWidth()
    )
}

@Composable
fun RecordTopBar(time: String) {
    TopAppBar(
        title = {
            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Icon(Icons.Rounded.Menu, "")
                Text(
                    text = time,
                    modifier = Modifier.padding(start = 5.dp),
                    style = MaterialTheme.typography.titleLarge
                )
            }
        },
        modifier = Modifier.fillMaxWidth(),
    )
}

@Composable
fun NoteTopBar(noteName: String) {
    TopAppBar(
        title = {
            Text(
                modifier = Modifier.padding(start = 5.dp),
                text = noteName,
                style = MaterialTheme.typography.titleLarge
            )
        },
        navigationIcon = {
            IconButton(onClick = {}) {
                Icon(Icons.Filled.ArrowBack, "")
            }
        },
        modifier = Modifier.fillMaxWidth()
    )
}

@Preview
@Composable
private fun PreviewApp() {
    CategoryListTopBar()
}

@Preview
@Composable
private fun PreviewAppRecord() {
    RecordTopBar("1:23")
}

@Preview
@Composable
private fun PreviewAppNote() {
    NoteTopBar("math")
}