package com.egoriku.grodnoroads.screen.settings

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.egoriku.grodnoroads.screen.settings.SettingsComponent.Pref
import com.egoriku.grodnoroads.screen.settings.store.SettingsStoreFactory.DialogState
import com.egoriku.grodnoroads.screen.settings.store.SettingsStoreFactory.DialogState.ThemeDialog
import com.egoriku.grodnoroads.screen.settings.store.SettingsStoreFactory.SettingsState
import com.egoriku.grodnoroads.screen.settings.ui.AppSettings
import com.egoriku.grodnoroads.screen.settings.ui.MapEventsSettings
import com.egoriku.grodnoroads.screen.settings.ui.MapPreferencesSettings
import com.egoriku.grodnoroads.screen.settings.ui.dialog.AppThemeDialog

@Composable
fun SettingsUi(settingsComponent: SettingsComponent) {
    val settingsState by settingsComponent.settingsState.collectAsState(initial = SettingsState())
    val dialogState by settingsComponent.dialogState.collectAsState(initial = DialogState.None)

    DialogHandler(
        dialogState = dialogState,
        closeDialog = settingsComponent::closeDialog,
        processResult = settingsComponent::processResult
    )

    Surface(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .statusBarsPadding()
                .padding(horizontal = 16.dp)
                .verticalScroll(state = rememberScrollState())
        ) {
            AppSettings(settingsState, settingsComponent::process)
            MapEventsSettings(
                mapInfo = settingsState.mapInfo,
                onCheckedChange = settingsComponent::onCheckedChanged
            )
            MapPreferencesSettings(
                mapAppearance = settingsState.mapAppearance,
                onCheckedChange = settingsComponent::onCheckedChanged
            )
        }
    }
}

@Composable
fun DialogHandler(
    dialogState: DialogState,
    closeDialog: () -> Unit,
    processResult: (Pref) -> Unit
) {
    when (dialogState) {
        is ThemeDialog -> {
            AppThemeDialog(
                dialogState = dialogState,
                closeDialog = closeDialog,
                processResult = processResult
            )
        }
        else -> {}
    }
}