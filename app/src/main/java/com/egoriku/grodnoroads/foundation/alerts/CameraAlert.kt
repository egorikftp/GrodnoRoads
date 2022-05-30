package com.egoriku.grodnoroads.foundation.alerts

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.pluralStringResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.egoriku.grodnoroads.R
import com.egoriku.grodnoroads.foundation.HSpacer
import com.egoriku.grodnoroads.foundation.SpeedLimitSign

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun CameraAlert(
    distance: Int, speedLimit: Int, drawableId: Int, title: String
) {
    Card(modifier = Modifier.fillMaxWidth(), elevation = 5.dp) {
        Column(modifier = Modifier.padding(8.dp)) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    modifier = Modifier.size(46.dp),
                    painter = painterResource(id = drawableId),
                    contentDescription = null
                )
                Column(
                    modifier = Modifier.weight(1f),
                ) {
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center,
                        text = title,
                        style = MaterialTheme.typography.body1
                    )
                    HSpacer(dp = 4.dp)
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center,
                        text = pluralStringResource(
                            R.plurals.camera_alerts_plurals_distance, distance, distance
                        ),
                        style = MaterialTheme.typography.body2
                    )
                }

                if (speedLimit > 0) {
                    SpeedLimitSign(limit = speedLimit, fontSize = 25.sp)
                }
            }
        }
    }
}

@Preview
@Preview(locale = "ru")
@Composable
private fun PreviewStationaryAlert() {
    Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
        CameraAlert(
            distance = 200,
            speedLimit = 60,
            drawableId = R.drawable.ic_stationary_camera,
            title = stringResource(R.string.alerts_stationary_camera)
        )
        CameraAlert(
            distance = 200,
            speedLimit = -1,
            drawableId = R.drawable.ic_stationary_camera,
            title = stringResource(R.string.alerts_stationary_camera)
        )
        CameraAlert(
            distance = 200,
            speedLimit = 60,
            drawableId = R.drawable.ic_mobile_camera,
            title = stringResource(R.string.alerts_mobile_camera)
        )
        CameraAlert(
            distance = 200,
            speedLimit = -1,
            drawableId = R.drawable.ic_mobile_camera,
            title = stringResource(R.string.alerts_mobile_camera)
        )
    }
}