package com.example.weather.permissions

import android.Manifest
import android.content.Context
import com.example.weather.R
import com.example.weather.permissions.Permission
import com.example.weather.permissions.PermissionProvider

data class CallPhonePermission(val context: Context) : Permission(
    context.getString(R.string.call_phone_permission_rationale),
    Manifest.permission.CALL_PHONE,
    PermissionProvider.CALL_PHONE_REQUEST_CODE,
    context.getString(R.string.call_phone_permission_required_dialog_title),
    context.getString(R.string.call_phone_permission_required_dialog_message)
)