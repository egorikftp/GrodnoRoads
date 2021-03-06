package com.egoriku.grodnoroads.screen.map.domain

import com.egoriku.grodnoroads.screen.map.domain.MapEvent.Reports
import com.google.android.gms.maps.model.LatLng

sealed interface MapAlertDialog {

    data class MarkerInfoDialog(val reports: Reports) : MapAlertDialog
    data class PoliceDialog(val currentLatLng: LatLng) : MapAlertDialog
    data class RoadIncidentDialog(val currentLatLng: LatLng) : MapAlertDialog
    object None : MapAlertDialog
}