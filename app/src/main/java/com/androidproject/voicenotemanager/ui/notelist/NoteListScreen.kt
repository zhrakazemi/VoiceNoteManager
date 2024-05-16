package com.androidproject.voicenotemanager.ui.notelist

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.androidproject.voicenotemanager.R
import com.androidproject.voicenotemanager.ui.NoteListTopBar

@Composable
fun NoteListScreen(
    notes: List<String>,
    categoryName: String
) {
    Scaffold(modifier = Modifier.fillMaxWidth(), floatingActionButton = {
        FloatingActionButton(onClick = {}) {
            Icon(Icons.Filled.Add, "")
        }
    }, topBar = {
        NoteListTopBar(categoryName)
    }) { innerPadding ->
        CategoryList(modifier = Modifier.padding(innerPadding), notes)
    }


}

@Composable
fun CategoryList(
    modifier: Modifier, notes: List<String>
) {
    LazyVerticalGrid(modifier = modifier, columns = GridCells.Fixed(2)) {
        items(notes) {
            NoteItem(name = it)
        }
    }
}

@Composable
fun NoteItem(name: String) {
    Card(
        modifier = Modifier.padding(8.dp),
    ) {
        Column(
            Modifier.aspectRatio(1f),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                modifier = Modifier
                    .fillMaxWidth(0.75f)
                    .padding(10.dp),
                painter = painterResource(id = R.drawable.logo_no_fill),
                contentDescription = name
            )
            Text(
                text = name,
                style = MaterialTheme.typography.titleLarge,
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun MainPreview() {
    val categories = listOf("section 1", "section 2", "section 3")
    NoteListScreen(categories, "math")
}
