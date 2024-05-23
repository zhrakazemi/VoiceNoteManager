package com.androidproject.voicenotemanager.ui.notelist

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.androidproject.voicenotemanager.R
import com.androidproject.voicenotemanager.ui.NoteListTopBar

@Composable
fun NoteListScreen(
    notes: List<String>,
    categoryName: String
) {
    val message = remember { mutableStateOf("") }
    val openDialog = remember { mutableStateOf(false) }
    val editMessage = remember { mutableStateOf("") }
    Scaffold(modifier = Modifier.fillMaxWidth(), floatingActionButton = {
        FloatingActionButton(onClick = {
            editMessage.value = message.value
            openDialog.value = true
        }) {
            Icon(Icons.Filled.Add, "")
        }
    }, topBar = {
        NoteListTopBar(categoryName)
    }) { innerPadding ->
        CategoryList(modifier = Modifier.padding(innerPadding), notes)
    }
    if (openDialog.value) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    color = contentColorFor(MaterialTheme.colorScheme.background)
                        .copy(alpha = 0.6f)
                )
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null,
                    onClick = {
                        openDialog.value = false
                    }
                ),
            contentAlignment = Alignment.Center
        ) {
            CreateNoteDialog(message, openDialog, editMessage)
        }
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

@Composable
fun CreateNoteDialog(
    message: MutableState<String>,
    openDialog: MutableState<Boolean>,
    editMessage: MutableState<String>
) {
    Dialog(onDismissRequest = {
        openDialog.value = false
    }) {
        Card(
            modifier = Modifier
                .fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
        ) {
            Column(
                modifier = Modifier.padding(25.dp),
            ) {
                Text(
                    text = "Create note",
                    fontSize = 24.sp
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(text = "Enter a name for note")

                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = editMessage.value,
                    onValueChange = { editMessage.value = it },
                    singleLine = true,
                    label = { (Text(text = "Name")) }
                )
            }
            Row(
                modifier = Modifier
                    .padding(end = 15.dp, bottom = 10.dp)
                    .align(Alignment.End)
            ) {
                TextButton(
                    onClick = {
                        openDialog.value = false
                    },

                    ) {
                    Text("Cancel")
                }

                Spacer(modifier = Modifier.width(8.dp))

                TextButton(
                    onClick = {
                        message.value = editMessage.value
                        openDialog.value = false
                    }
                ) {
                    Text("OK")
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun MainPreview() {
    val categories = listOf("section 1", "section 2", "section 3")
    NoteListScreen(categories, "math")
}
