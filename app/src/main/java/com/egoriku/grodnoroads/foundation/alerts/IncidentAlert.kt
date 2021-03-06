package com.egoriku.grodnoroads.foundation.alerts

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.pluralStringResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.egoriku.grodnoroads.R
import com.egoriku.grodnoroads.foundation.HSpacer
import com.egoriku.grodnoroads.foundation.alerts.common.MessageComponent
import com.egoriku.grodnoroads.screen.map.domain.MapEventType
import com.egoriku.grodnoroads.screen.map.domain.MapEventType.TrafficPolice
import com.egoriku.grodnoroads.screen.map.domain.MessageItem
import com.egoriku.grodnoroads.screen.map.domain.Source

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun IncidentAlert(
    emoji: String,
    title: String,
    distance: Int,
    messages: List<MessageItem>
) {
    Card(modifier = Modifier.fillMaxWidth(), elevation = 5.dp) {
        Column(modifier = Modifier.padding(8.dp)) {
            Column(
                modifier = Modifier.align(Alignment.CenterHorizontally),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    textAlign = TextAlign.Center,
                    text = buildString {
                        append(emoji)
                        append(" ")
                        append(title)
                    },
                    style = MaterialTheme.typography.body1,
                )
                HSpacer(dp = 4.dp)
                Text(
                    text = pluralStringResource(
                        R.plurals.camera_alerts_plurals_distance,
                        distance,
                        distance
                    ),
                    style = MaterialTheme.typography.body2
                )
            }
            Divider(modifier = Modifier.padding(vertical = 8.dp))
            MessageComponent(messages = messages)
        }
    }
}

@Preview
@Preview(locale = "ru")
@Composable
fun PreviewIncidentAlert() {
    Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
        IncidentAlert(
            emoji = MapEventType.RoadIncident.emoji,
            title = stringResource(R.string.alerts_incident),
            distance = 200,
            messages = listOf(
                MessageItem(
                    message = "(15:30) ???????????? ???????? ?????? ?? ???????????? ???????????? ???? ?????????????????????? ???? ???????????? ?? ??????????",
                    source = Source.App
                ),
                MessageItem(
                    message = "(15:45) ?????????? ???????? ?? ?????????? ???????????? ???? ??????????????????????",
                    source = Source.Viber
                ),
                MessageItem(message = "(15:50) ?????????? ????????", source = Source.Telegram)
            )
        )
        IncidentAlert(
            emoji = TrafficPolice.emoji,
            title = stringResource(R.string.alerts_traffic_police),
            distance = 350,
            messages = listOf(
                MessageItem(
                    message = "(15:30) ?????????????????????? ???????????????????????? ???? ????????????????",
                    source = Source.App
                )
            )
        )
    }
}