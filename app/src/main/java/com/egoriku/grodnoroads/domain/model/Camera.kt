package com.egoriku.grodnoroads.domain.model

import com.google.android.gms.maps.model.LatLng

data class Camera(
    val message: String,
    val speed: Int,
    val position: LatLng
)