package com.example.weather.permissions

import android.app.Activity
import androidx.fragment.app.Fragment
import pub.devrel.easypermissions.EasyPermissions

interface PermissionProviderHost {
    val activity: Activity

    val fragmentOrActivity: Any

    fun requestPermission(permission: Permission)

    fun hasPermission(permission: Permission): Boolean {
        return EasyPermissions.hasPermissions(activity, permission.name)
    }
}

class PermissionProviderHostActivity(override val activity: Activity) : PermissionProviderHost {
    override val fragmentOrActivity: Any = activity

    override fun requestPermission(permission: Permission) {
        EasyPermissions.requestPermissions(
            activity,
            permission.rationale,
            permission.requestCode,
            permission.name
        )
    }
}

class PermissionProviderHostFragment(private val fragment: Fragment) : PermissionProviderHost {
    override val fragmentOrActivity: Any = fragment

    override val activity = fragment.requireActivity()
    override fun requestPermission(permission: Permission) {
        EasyPermissions.requestPermissions(
            fragment,
            permission.rationale,
            permission.requestCode,
            permission.name
        )
    }
}