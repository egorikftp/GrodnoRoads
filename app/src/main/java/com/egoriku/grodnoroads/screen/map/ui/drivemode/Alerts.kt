package com.egoriku.grodnoroads.screen.map.ui.drivemode

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.egoriku.grodnoroads.R
import com.egoriku.grodnoroads.foundation.alerts.CameraAlert
import com.egoriku.grodnoroads.foundation.alerts.IncidentAlert
import com.egoriku.grodnoroads.screen.map.domain.Alert
import com.egoriku.grodnoroads.screen.map.domain.Alert.CameraAlert
import com.egoriku.grodnoroads.screen.map.domain.Alert.IncidentAlert
import com.egoriku.grodnoroads.screen.map.domain.MapEventType.*
import com.egoriku.grodnoroads.screen.map.domain.MessageItem
import com.egoriku.grodnoroads.screen.map.domain.Source

@Composable
fun Alerts(
    modifier: Modifier = Modifier,
    alerts: List<Alert>
) {
    LazyColumn(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        items(alerts) { alert ->
            when (alert) {
                is IncidentAlert -> {
                    val title = when (alert.mapEventType) {
                        RoadAccident -> stringResource(R.string.alerts_accident)
                        TrafficPolice -> stringResource(R.string.alerts_police)
                        else -> throw IllegalArgumentException("title not applicable")
                    }

                    IncidentAlert(
                        title = title,
                        distance = alert.distance,
                        messages = alert.messages
                    )

                }
                is CameraAlert -> {
                    val title = when (alert.mapEventType) {
                        StationaryCamera -> stringResource(R.string.alerts_stationary_camera)
                        MobileCamera -> stringResource(R.string.alerts_mobile_camera)
                        else -> throw IllegalArgumentException("title not applicable")
                    }
                    val icon = when (alert.mapEventType) {
                        StationaryCamera -> R.drawable.ic_stationary_camera
                        MobileCamera -> R.drawable.ic_mobile_camera
                        else -> throw IllegalArgumentException("title not applicable")
                    }
                    CameraAlert(
                        distance = alert.distance,
                        speedLimit = alert.speedLimit,
                        drawableId = icon,
                        title = title
                    )
                }
            }
        }
    }
}

@Preview
@Preview(locale = "ru")
@Composable
private fun AlertsPreview() {
    Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
        Alerts(
            alerts = listOf(
                IncidentAlert(
                    mapEventType = TrafficPolice,
                    distance = 1,
                    messages = listOf(
                        MessageItem(
                            message = "Славинского беларуснефть на скорость",
                            source = Source.Viber
                        )
                    )
                )
            )
        )
        Alerts(
            alerts = listOf(
                CameraAlert(
                    distance = 2,
                    speedLimit = 60,
                    mapEventType = StationaryCamera
                )
            )
        )
        Alerts(
            alerts = listOf(
                IncidentAlert(
                    distance = 5,
                    messages = listOf(
                        MessageItem(
                            message = "(15:30) Старый мост ДТП в правой полосе по направлению от кольца в центр",
                            source = Source.Viber
                        ),
                        MessageItem(
                            message = "(15:45) Новый мост в левой полосе по направлению",
                            source = Source.Viber
                        )
                    ),
                    mapEventType = RoadAccident
                )
            )
        )
        Alerts(
            alerts = listOf(
                CameraAlert(distance = 220, speedLimit = -1, mapEventType = MobileCamera)
            )
        )
        Alerts(
            alerts = listOf(
                CameraAlert(distance = 220, speedLimit = 60, mapEventType = MobileCamera)
            )
        )
    }
}