package com.example.weather.permissions

import pub.devrel.easypermissions.EasyPermissions
import android.util.Log
import javax.inject.Inject

class PermissionProvider @Inject constructor(
    private val accessFineLocationPermission: AccessFineLocationPermission,
    private val callPhonePermission: CallPhonePermission
) : EasyPermissions.PermissionCallbacks {
    lateinit var permissionProviderHost: PermissionProviderHost

    companion object {
        const val LOCATION_REQUEST_CODE = 101
        const val CALL_PHONE_REQUEST_CODE = 102
    }

    var onGranted: (() -> Unit)? = null
    var onDenied: ((permission: Permission, isPermanentlyDenied: Boolean) -> Unit)? = null

    fun withPermission(permission: Permission, callback: () -> Unit) {

        if (permissionProviderHost.hasPermission(permission)) {
            callback()
        } else {
            permissionProviderHost.requestPermission(permission)
        }
    }

    override fun onPermissionsDenied(requestCode: Int, permissions: MutableList<String>) {
        Log.d(PermissionProvider::class.java.name, "onPermissionsDenied")
        when (requestCode) {
            LOCATION_REQUEST_CODE -> onDenied?.invoke(
                accessFineLocationPermission,
                isPermissionPermanentlyDenied(accessFineLocationPermission)
            )
            CALL_PHONE_REQUEST_CODE -> onDenied?.invoke(
                callPhonePermission,
                isPermissionPermanentlyDenied(callPhonePermission)
            )
        }
    }

    override fun onPermissionsGranted(requestCode: Int, perms: MutableList<String>) {
        onGranted?.invoke()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        EasyPermissions.onRequestPermissionsResult(
            requestCode,
            permissions,
            grantResults,
            permissionProviderHost.fragmentOrActivity,
            this
        )
    }

    private fun isPermissionPermanentlyDenied(permission: Permission): Boolean {
        return EasyPermissions.permissionPermanentlyDenied(
            permissionProviderHost.activity,
            permission.name
        )
    }
}
