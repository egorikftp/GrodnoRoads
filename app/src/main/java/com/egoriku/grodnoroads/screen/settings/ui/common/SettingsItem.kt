package com.egoriku.grodnoroads.screen.settings.ui.common

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForwardIos
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun SettingsItem(
    icon: ImageVector,
    text: String,
    onClick: () -> Unit
) {
    ListItem(
        modifier = Modifier
            .clickable(onClick = onClick)
            .padding(horizontal = 16.dp),
        icon = {
            Icon(
                imageVector = icon,
                contentDescription = null
            )
        },
        text = {
            Text(text = text)
        },
        trailing = {
            Icon(
                Icons.Filled.ArrowForwardIos,
                contentDescription = null
            )
        }
    )
    Divider()
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun SettingsItem(
    icon: ImageVector,
    text: String,
    value: String,
    onClick: () -> Unit
) {
    ListItem(
        modifier = Modifier
            .clickable(onClick = onClick)
            .padding(horizontal = 8.dp),
        icon = {
            Icon(
                imageVector = icon,
                contentDescription = null
            )
        },
        text = {
            Text(text = text)
        },
        trailing = {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(text = value, color = MaterialTheme.colors.secondary)
                Icon(
                    imageVector = Icons.Default.ChevronRight,
                    contentDescription = null,
                    tint = MaterialTheme.colors.secondary
                )
            }
        }
    )
    Divider()
}