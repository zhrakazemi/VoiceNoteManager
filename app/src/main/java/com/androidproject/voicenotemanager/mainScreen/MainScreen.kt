package com.androidproject.voicenotemanager.mainScreen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.androidproject.voicenotemanager.R

@Composable
fun MainScreen() {
    Scaffold(
        modifier = Modifier.fillMaxWidth(),
        floatingActionButton = {
            FloatingActionButton(onClick = {}) {
                Icon(Icons.Filled.Add, "")
            }
        }
    ) {
        +
    }


}

@Composable
fun CategoryList() {
    LazyColumn {

    }
}

@Composable
fun NoteList() {

}

@Composable
fun CategoryItem(name: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
            .clickable { },
        verticalAlignment = Alignment.CenterVertically,


        ) {
        Text(text = name)
    }
}