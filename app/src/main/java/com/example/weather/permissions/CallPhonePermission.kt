package com.example.weather.permissions

import android.Manifest
import android.content.Context
import com.example.weather.R
import com.example.weather.permissions.Permission
import com.example.weather.permissions.PermissionProvider
import javax.inject.Inject

class CallPhonePermission (val context: Context) : Permission {
    override val name: String
        get() = Manifest.permission.CALL_PHONE
    override val rationale: String
        get() = context.getString(R.string.call_phone_permission_rationale)
    override val requestCode: Int
        get() = PermissionProvider.CALL_PHONE_REQUEST_CODE
    override val requiredPermissionDialogMessage: String
        get() = context.getString(R.string.call_phone_permission_required_dialog_message)
    override val requiredPermissionDialogTitle: String
        get() = context.getString(R.string.call_phone_permission_required_dialog_title)
}