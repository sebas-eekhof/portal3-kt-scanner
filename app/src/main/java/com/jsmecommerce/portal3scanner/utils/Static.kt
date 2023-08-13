package com.jsmecommerce.portal3scanner.utils

import android.Manifest
import com.jsmecommerce.portal3scanner.models.Permission
import com.jsmecommerce.portal3scanner.R
import java.util.Locale

class Static {
    companion object {
        val requiredPermissions = listOf(
            Permission(
                Manifest.permission.ACCESS_BACKGROUND_LOCATION,
                R.drawable.ic_location_pause,
                R.string.permissions_location_background_title,
                R.string.permissions_location_background_description
            ),
            Permission(
                Manifest.permission.ACCESS_COARSE_LOCATION,
                R.drawable.ic_location,
                R.string.permissions_location_coarse_title,
                R.string.permissions_location_coarse_description
            ),
            Permission(
                Manifest.permission.ACCESS_FINE_LOCATION,
                R.drawable.ic_location,
                R.string.permissions_location_fine_title,
                R.string.permissions_location_fine_description
            ),
            Permission(
                Manifest.permission.CAMERA,
                R.drawable.ic_camera,
                R.string.permissions_camera_title,
                R.string.permissions_camera_description
            )
        )
        val locales = listOf(
            Locale("nl"),
            Locale("en")
        )
    }
}