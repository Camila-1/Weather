package com.example.weather.permissions

import android.Manifest
import android.content.Context
import com.example.weather.R
import com.example.weather.permissions.Permission
import com.example.weather.permissions.PermissionProvider

data class AccessFineLocationPermission(val context: Context) : Permission(
    context.getString(R.string.location_permission_rationale),
    Manifest.permission.ACCESS_FINE_LOCATION,
    PermissionProvider.LOCATION_REQUEST_CODE,
    context.getString(R.string.location_permission_required_dialog_title),
    context.getString(R.string.location_permission_required_dialog_message)
)
