package com.example.weather.permissions

open class Permission(
    open val rationale: String, val name: String, val requestCode: Int,
    val requiredPermissionDialogTitle: String,
    val requiredPermissionDialogMessage: String
)