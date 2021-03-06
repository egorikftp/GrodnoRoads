package com.egoriku.grodnoroads.screen.map.store

import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.core.utils.ExperimentalMviKotlinApi
import com.arkivanov.mvikotlin.extensions.coroutines.coroutineExecutorFactory
import com.egoriku.grodnoroads.screen.map.domain.MapAlertDialog
import com.egoriku.grodnoroads.screen.map.domain.MapEvent
import com.egoriku.grodnoroads.screen.map.store.DialogStoreFactory.*
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.Dispatchers

interface DialogStore : Store<Intent, State, Message>

class DialogStoreFactory(private val storeFactory: StoreFactory) {

    sealed interface Intent {
        data class OpenMarkerInfoDialog(val reports: MapEvent.Reports) : Intent
        data class OpenReportTrafficPoliceDialog(val latLng: LatLng) : Intent
        data class OpenRoadIncidentDialog(val latLng: LatLng) : Intent
        object CloseDialog : Intent
    }

    sealed interface Message {
        data class OpenDialog(val dialog: MapAlertDialog) : Message
        data class CloseDialog(val dialog: MapAlertDialog.None) : Message
    }

    data class State(
        val mapAlertDialog: MapAlertDialog = MapAlertDialog.None
    )

    @OptIn(ExperimentalMviKotlinApi::class)
    fun create(): DialogStore =
        object : DialogStore, Store<Intent, State, Message> by storeFactory.create(
            initialState = State(),
            executorFactory = coroutineExecutorFactory(Dispatchers.Main) {
                onIntent<Intent.OpenMarkerInfoDialog> { dialog ->
                    dispatch(
                        Message.OpenDialog(dialog = MapAlertDialog.MarkerInfoDialog(dialog.reports))
                    )
                }
                onIntent<Intent.OpenReportTrafficPoliceDialog> { data ->
                    dispatch(
                        Message.OpenDialog(
                            dialog = MapAlertDialog.PoliceDialog(
                                currentLatLng = data.latLng
                            )
                        )
                    )
                }
                onIntent<Intent.OpenRoadIncidentDialog> { data ->
                    dispatch(
                        Message.OpenDialog(
                            dialog = MapAlertDialog.RoadIncidentDialog(
                                currentLatLng = data.latLng
                            )
                        )
                    )
                }
                onIntent<Intent.CloseDialog> {
                    dispatch(
                        Message.CloseDialog(dialog = MapAlertDialog.None)
                    )
                }
            },
            reducer = { message: Message ->
                when (message) {
                    is Message.OpenDialog -> copy(mapAlertDialog = message.dialog)
                    is Message.CloseDialog -> copy(mapAlertDialog = message.dialog)
                }
            }
        ) {}
}