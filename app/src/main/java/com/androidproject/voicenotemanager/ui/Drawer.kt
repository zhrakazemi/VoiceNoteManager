package com.androidproject.voicenotemanager.ui

import android.app.Activity
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Button
import androidx.compose.material3.DrawerState
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.androidproject.voicenotemanager.Destinations
import com.androidproject.voicenotemanager.NavigationActions
import com.androidproject.voicenotemanager.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch


@Composable
fun AppModalDrawer(
    drawerState: DrawerState,
    currentRoute: String,
    navigationActions: NavigationActions,
    coroutineScope: CoroutineScope = rememberCoroutineScope(),
    content: @Composable () -> Unit
) {
    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            AppDrawer(
                currentRoute = currentRoute,
                closeDrawer = { coroutineScope.launch { drawerState.close() } },
                navigationActions = navigationActions
            )
        }
    ) {
        content()
    }
}


@Composable
private fun AppDrawer(
    currentRoute: String,
    closeDrawer: () -> Unit,
    navigationActions: NavigationActions,
    modifier: Modifier = Modifier
) {
    ModalDrawerSheet {
        DrawerHeader()
        DrawerButton(
            imageVector = Icons.Filled.Home,
            label = "Home",
            isSelected = currentRoute == Destinations.CATEGORY_LIST_ROUTE,
            action = {
                navigationActions.navigateToCategoryList()
                closeDrawer()
            }
        )
        val activity = (LocalContext.current as? Activity)
        DrawerButton(
            imageVector = Icons.Filled.ExitToApp,
            label = "Exit",
            isSelected = false,
            action = {
                activity?.finish()
            }
        )
    }
}

@Composable
private fun DrawerHeader(
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.onSurface)
            .height(192.dp)
            .padding(16.dp)
    ) {
        Image(
            painter = painterResource(id = R.drawable.logo_no_fill),
            contentDescription = "",
            modifier = Modifier.width(100.dp)
        )
        Text(
            text = stringResource(id = R.string.app_name),
        )
    }
}

@Composable
private fun DrawerButton(
    imageVector: ImageVector,
    label: String,
    action: () -> Unit,
    isSelected: Boolean,
    modifier: Modifier = Modifier
) {
    val tintColor = if (isSelected) {
        MaterialTheme.colorScheme.secondary
    } else {
        MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
    }
    TextButton(
        onClick = action,
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
    ) {
        Row(
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Icon(
                imageVector = imageVector,
                contentDescription = null, // decorative
                tint = tintColor
            )
            Spacer(Modifier.width(16.dp))
            Text(
                text = label,
                style = MaterialTheme.typography.bodyMedium,
                color = tintColor
            )
        }
    }
}

@Preview("Drawer contents")
@Composable
fun PreviewAppDrawer() {


}
