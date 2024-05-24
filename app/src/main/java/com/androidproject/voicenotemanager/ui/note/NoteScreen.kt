package com.androidproject.voicenotemanager.ui.note

import androidx.compose.animation.core.EaseIn
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.Image
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults.buttonColors
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.unit.dp
import com.androidproject.voicenotemanager.NavigationActions
import com.androidproject.voicenotemanager.ui.NoteTopBar

@Composable
fun NoteScreen(
    note: String = "",
    time: String = "",
    noteId: String?,
    onBack: () -> Unit,
    navigationActions: NavigationActions
) {
    Scaffold(modifier = Modifier.fillMaxWidth(), floatingActionButton = {
        Row(
            modifier = Modifier.fillMaxWidth(.9f),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.Bottom
        ) {
            RecordFloatingActionButton(time = time, navigationActions , noteId)
            val expand by remember {
                mutableStateOf(true)
            }
            CustomFloatingActionButton(expandable = expand, onFabClick = { /*TODO*/ })
        }
    }, topBar = {
        NoteTopBar(note, onBack)
    }) { innerPadding ->
        Column(Modifier.padding(innerPadding)) {

        }
    }
}

@Composable
fun RecordFloatingActionButton(time: String, navigationActions: NavigationActions , noteId : String?) {
    ExtendedFloatingActionButton(
        onClick = {
            navigationActions.navigateToRecord(noteId)
        },
        icon = { Icon(Icons.Filled.Mic, "") },
        text = { Text(text = time) },
    )
}

@Composable
fun CustomFloatingActionButton(
    expandable: Boolean,
    onFabClick: () -> Unit,
) {
    var isExpanded by remember { mutableStateOf(false) }
    if (!expandable) {
        isExpanded = false
    }

    val fabSize = 64.dp
    val expandedFabWidth by animateDpAsState(
        targetValue = if (isExpanded) 200.dp else fabSize,
        animationSpec = spring(dampingRatio = 3f), label = ""
    )
    val expandedFabHeight by animateDpAsState(
        targetValue = if (isExpanded) 58.dp else fabSize,
        animationSpec = spring(dampingRatio = 3f), label = ""
    )

    Column {
        Box(
            modifier = Modifier
                .offset(y = (25).dp)
                .size(
                    width = expandedFabWidth,
                    height = (animateDpAsState(
                        if (isExpanded) 130.dp else 0.dp,
                        animationSpec = spring(dampingRatio = 4f), label = ""
                    )).value
                )
                .background(
                    MaterialTheme.colorScheme.onPrimaryContainer,
                    shape = RoundedCornerShape(18.dp)
                ),
        ) {
            Column {
                Button(
                    modifier = Modifier
                        .fillMaxWidth()
                        .size(50.dp),
                    onClick = { /*TODO*/ },
                    colors = buttonColors(
                        MaterialTheme.colorScheme.onPrimaryContainer,
                    ),

                    ) {
                    Row(
                        modifier = Modifier.fillMaxSize(),
                        horizontalArrangement = Arrangement.Start,
                        verticalAlignment = Alignment.CenterVertically
                    ) {

                        Icon(
                            imageVector = Icons.Filled.Image,
                            contentDescription = null,

                        )
                        Spacer(modifier = Modifier.padding(horizontal = 2.dp))
                        Text(text = "Gallery")
                    }
                }
                Spacer(modifier = Modifier.padding(2.dp))
                Button(
                    modifier = Modifier
                        .fillMaxWidth()
                        .size(50.dp), onClick = { /*TODO*/ },
                    colors = buttonColors(
                        MaterialTheme.colorScheme.onPrimaryContainer,
                    )
                ) {
                    Row(
                        modifier = Modifier.fillMaxSize(),
                        horizontalArrangement = Arrangement.Start,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Filled.CameraAlt,
                            contentDescription = null,
                        )
                        Spacer(modifier = Modifier.padding(horizontal = 2.dp))
                        Text(text = "Camera")
                    }
                }
            }

        }

        FloatingActionButton(
            onClick = {
                onFabClick()
                if (expandable) {
                    isExpanded = !isExpanded
                }
            },
            modifier = Modifier
                .width(expandedFabWidth)
                .height(expandedFabHeight),
            shape = RoundedCornerShape(18.dp)

        ) {

            Icon(
                imageVector = Icons.Filled.Add,
                contentDescription = null,
                modifier = Modifier
                    .size(24.dp)
                    .offset(
                        x = animateDpAsState(
                            if (isExpanded) (-70).dp else 0.dp,
                            animationSpec = spring(dampingRatio = 3f), label = ""
                        ).value
                    )
            )

            Text(
                text = "Add Attachment",
                softWrap = false,
                modifier = Modifier
                    .offset(
                        x = animateDpAsState(
                            if (isExpanded) 10.dp else 50.dp,
                            animationSpec = spring(dampingRatio = 3f), label = ""
                        ).value
                    )
                    .alpha(
                        animateFloatAsState(
                            targetValue = if (isExpanded) 1f else 0f,
                            animationSpec = tween(
                                durationMillis = if (isExpanded) 350 else 100,
                                delayMillis = if (isExpanded) 100 else 0,
                                easing = EaseIn
                            ), label = ""
                        ).value
                    )
            )

        }
    }
}