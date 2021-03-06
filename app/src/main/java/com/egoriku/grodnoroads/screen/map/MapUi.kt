package com.egoriku.grodnoroads.screen.map

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.egoriku.grodnoroads.extension.toast
import com.egoriku.grodnoroads.screen.map.domain.AppMode
import com.egoriku.grodnoroads.screen.map.domain.GrodnoRoadsMapPreferences
import com.egoriku.grodnoroads.screen.map.domain.LocationState
import com.egoriku.grodnoroads.screen.map.domain.MapAlertDialog.*
import com.egoriku.grodnoroads.screen.map.store.LocationStoreFactory.Label
import com.egoriku.grodnoroads.screen.map.store.LocationStoreFactory.Label.ShowToast
import com.egoriku.grodnoroads.screen.map.store.MapEventsStoreFactory.Intent.ReportAction
import com.egoriku.grodnoroads.screen.map.ui.GoogleMapView
import com.egoriku.grodnoroads.screen.map.ui.MarkerAlertDialog
import com.egoriku.grodnoroads.screen.map.ui.defaultmode.MapMode
import com.egoriku.grodnoroads.screen.map.ui.dialog.IncidentDialog
import com.egoriku.grodnoroads.screen.map.ui.dialog.ReportDialog
import com.egoriku.grodnoroads.screen.map.ui.drivemode.DriveMode

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun MapUi(
    component: MapComponent,
    openDrawer: () -> Unit
) {
    MarkerAlertDialogComponent(component)

    Surface(
        modifier = Modifier
            .fillMaxSize()
            .navigationBarsPadding()
    ) {
        val location by component.location.collectAsState(LocationState.None)
        val mode by component.appMode.collectAsState(AppMode.Map)
        val mapEvents by component.mapEvents.collectAsState(initial = emptyList())
        val mapPreferences by component.mapPreferences.collectAsState(initial = GrodnoRoadsMapPreferences.Default)
        val alerts by component.alerts.collectAsState(initial = emptyList())

        LabelsSubscription(component)

        Box(modifier = Modifier.fillMaxSize()) {
            GoogleMapView(
                modifier = Modifier.fillMaxSize(),
                mapEvents = mapEvents,
                mode = mode,
                mapPreferences = mapPreferences,
                locationState = location,
                onMarkerClick = {
                    component.showMarkerInfoDialog(reports = it)
                }
            )

            AnimatedContent(targetState = mode) { state ->
                when (state) {
                    AppMode.Map -> {
                        MapMode(
                            onLocationEnabled = component::startLocationUpdates,
                            onLocationDisabled = component::onLocationDisabled,
                            openDrawer = openDrawer
                        )
                    }
                    AppMode.Drive -> {
                        DriveMode(
                            alerts = alerts,
                            location = location,
                            stopDrive = component::stopLocationUpdates,
                            reportPolice = {
                                if (location != LocationState.None) {
                                    component.openReportFlow(
                                        reportDialogFlow = MapComponent.ReportDialogFlow.TrafficPolice(
                                            location.latLng
                                        )
                                    )
                                }
                            },
                            reportIncident = {
                                if (location != LocationState.None) {
                                    component.openReportFlow(
                                        reportDialogFlow = MapComponent.ReportDialogFlow.RoadIncident(
                                            location.latLng
                                        )
                                    )
                                }
                            }
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun MarkerAlertDialogComponent(component: MapComponent) {
    val mapAlertDialog by component.mapAlertDialog.collectAsState(initial = None)

    when (val state = mapAlertDialog) {
        is MarkerInfoDialog -> {
            MarkerAlertDialog(
                reports = state.reports,
                onClose = {
                    component.closeDialog()
                }
            )
        }
        is PoliceDialog -> {
            ReportDialog(
                onClose = { component.closeDialog() },
                onSend = { mapEvent, shortMessage, message ->
                    component.reportAction(
                        ReportAction.Params(
                            latLng = state.currentLatLng,
                            mapEventType = mapEvent,
                            shortMessage = shortMessage,
                            message = message
                        )
                    )
                }
            )
        }
        is RoadIncidentDialog -> {
            IncidentDialog(
                onClose = { component.closeDialog() },
                onSend = { mapEvent, shortMessage, message ->
                    component.reportAction(
                        ReportAction.Params(
                            latLng = state.currentLatLng,
                            mapEventType = mapEvent,
                            shortMessage = shortMessage,
                            message = message
                        )
                    )
                }
            )
        }
        is None -> Unit
    }
}

@Composable
private fun LabelsSubscription(component: MapComponent) {
    val context = LocalContext.current
    val labels by component.labels.collectAsState(initial = Label.None)

    LaunchedEffect(labels) {
        when (val label = labels) {
            is ShowToast -> context.toast(label.message)
            is Label.None -> {}
        }
    }
}