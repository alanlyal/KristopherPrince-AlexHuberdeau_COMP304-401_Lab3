package com.example.kristopherprince_alexhuberdeau_comp304_401_lab3.ui.screens

// LocalActivity.kt
import android.app.Activity
import androidx.compose.runtime.compositionLocalOf

val LocalActivity = compositionLocalOf<Activity> {
    error("LocalActivity not provided")
}
