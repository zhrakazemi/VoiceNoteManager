package com.androidproject.voicenotemanager.ui.categorylist

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.androidproject.voicenotemanager.ui.CategoryListTopBar


@Composable
fun CategoryListScreen(
    categories: List<String>,
    ) {
    Scaffold(modifier = Modifier.fillMaxWidth(), floatingActionButton = {
        FloatingActionButton(onClick = {}) {
            Icon(Icons.Filled.Add, "")
        }
    }, topBar = {
        CategoryListTopBar()
    }) { innerPadding ->
        CategoryList(modifier = Modifier.padding(innerPadding), categories)
    }


}

@Composable
fun CategoryList(
    modifier: Modifier, categories: List<String>
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

@Preview(showBackground = true)
@Composable
private fun MainPreview() {
    val categories = listOf("math", "programming", "other")
    CategoryListScreen(categories)
}

