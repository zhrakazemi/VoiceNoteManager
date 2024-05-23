package com.androidproject.voicenotemanager.ui.categorylist

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.androidproject.voicenotemanager.ui.CategoryListTopBar


@Composable
fun CategoryListScreen(
    openDrawer: () -> Unit,
    categories: List<String>,
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
        CategoryListTopBar(openDrawer)
    }) { innerPadding ->
        CategoryList(modifier = Modifier.padding(innerPadding), categories)

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
            CreateCategoryDialog(message, openDialog, editMessage)
        }
    }
}

@Composable
fun CategoryList(
    modifier: Modifier, categories: List<String>,
) {
    LazyColumn(modifier) {
        items(categories) {
            CategoryItem(name = it, 1)
        }
    }
}

@Composable
fun CategoryItem(name: String, number: Int) {
    Column(
        modifier = Modifier
            .padding(vertical = 2.dp, horizontal = 3.dp)
            .fillMaxSize()
            .shadow(
                elevation = 1.dp, shape = RoundedCornerShape(8.dp)
            )
            .size(75.dp)
            .background(Color.White)
    ) {
        Text(
            modifier = Modifier.padding(horizontal = 10.dp, vertical = 7.dp),
            text = name,
            style = MaterialTheme.typography.titleLarge,
        )
        Text(
            modifier = Modifier.padding(horizontal = 10.dp),
            text = "items : $number",
        )
    }
}


@Composable
fun CreateCategoryDialog(
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
                    text = "Create category",
                    fontSize = 24.sp
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(text = "Enter a name for category")

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
    val categories = listOf("math", "programming", "other")
    //CategoryListScreen(categories)
}

