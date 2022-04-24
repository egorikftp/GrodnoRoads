package com.egoriku.grodnoroads

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.egoriku.grodnoroads.domain.model.Camera
import com.egoriku.grodnoroads.domain.model.CameraType.Stationary
import com.egoriku.grodnoroads.ui.GoogleMapView
import com.egoriku.grodnoroads.ui.StartDriveModButton
import com.egoriku.grodnoroads.ui.theme.GrodnoRoadsTheme
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import org.koin.androidx.viewmodel.ext.android.viewModel

private val stationaryCameras = listOf(
    Camera(
        type = Stationary,
        message = "Стационарная камера",
        speed = 70,
        position = LatLng(53.647136, 23.811177)
    )
)

class MainActivity : ComponentActivity() {

    private val cameraViewModel: CameraViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            GrodnoRoadsTheme {
                Box(modifier = Modifier.fillMaxSize()) {
                    val stationary by cameraViewModel.stationary.collectAsState()

                    GoogleMapView(
                        modifier = Modifier.matchParentSize(),
                        stationary = stationary
                    )
                    StartDriveModButton(
                        modifier = Modifier
                            .align(Alignment.BottomCenter)
                    ) {
                        //todo Start Navigation
                    }
                }
            }
        }
    }
}