package com.example.weather.permissions

interface Permission {
    val rationale: String
    val name: String
    val requestCode: Int
    val requiredPermissionDialogTitle: String
    val requiredPermissionDialogMessage: String
}

