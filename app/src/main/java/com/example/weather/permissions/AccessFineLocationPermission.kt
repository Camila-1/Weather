package com.example.weather.permissions

import android.Manifest
import android.content.Context
import com.example.weather.R
import com.example.weather.permissions.Permission
import com.example.weather.permissions.PermissionProvider
import javax.inject.Inject

data class AccessFineLocationPermission (val context: Context) : Permission {
    override val rationale: String
        get() = context.getString(R.string.location_permission_rationale)
    override val name: String
        get() = Manifest.permission.ACCESS_FINE_LOCATION
    override val requestCode: Int
        get() = PermissionProvider.LOCATION_REQUEST_CODE
    override val requiredPermissionDialogTitle: String
        get() = context.getString(R.string.location_permission_required_dialog_title)
    override val requiredPermissionDialogMessage: String
        get() = context.getString(R.string.location_permission_required_dialog_message)

}

