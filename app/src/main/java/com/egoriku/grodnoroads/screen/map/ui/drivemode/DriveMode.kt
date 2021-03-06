package com.egoriku.grodnoroads.screen.map.ui.drivemode

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.egoriku.grodnoroads.R
import com.egoriku.grodnoroads.foundation.CurrentSpeedRect
import com.egoriku.grodnoroads.foundation.KeepScreenOn
import com.egoriku.grodnoroads.screen.map.domain.Alert
import com.egoriku.grodnoroads.screen.map.domain.LocationState
import com.egoriku.grodnoroads.screen.map.ui.drivemode.action.CloseAction
import com.egoriku.grodnoroads.screen.map.ui.drivemode.action.ReportAction

@Composable
fun DriveMode(
    alerts: List<Alert>,
    location: LocationState,
    stopDrive: () -> Unit,
    reportPolice: () -> Unit,
    reportIncident: () -> Unit
) {
    KeepScreenOn()
    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CurrentSpeedRect(
                modifier = Modifier.statusBarsPadding(),
                speed = location.speed.toString()
            )
            Alerts(alerts = alerts)
        }
        Row(
            horizontalArrangement = Arrangement.SpaceAround,
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .padding(bottom = 48.dp)
        ) {
            ReportAction(
                painter = painterResource(id = R.drawable.ic_traffic_police),
                onClick = reportPolice
            )
            ReportAction(
                painter = painterResource(id = R.drawable.ic_warning),
                onClick = reportIncident
            )
        }
        CloseAction(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .navigationBarsPadding()
                .padding(bottom = 16.dp),
            imageVector = Icons.Default.Close,
            onClick = stopDrive
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun DriveModePReview() {
    DriveMode(
        alerts = emptyList(),
        location = LocationState.None,
        stopDrive = {},
        reportPolice = {},
        reportIncident = {}
    )
}