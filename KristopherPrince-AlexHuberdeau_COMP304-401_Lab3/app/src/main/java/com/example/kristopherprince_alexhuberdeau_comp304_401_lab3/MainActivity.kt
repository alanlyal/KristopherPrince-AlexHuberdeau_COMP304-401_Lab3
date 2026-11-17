package com.example.kristopherprince_alexhuberdeau_comp304_401_lab3

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import com.example.kristopherprince_alexhuberdeau_comp304_401_lab3.ui.navigation.AppNavigation
import com.example.kristopherprince_alexhuberdeau_comp304_401_lab3.ui.theme.ProductAppTheme

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ProductAppTheme {
                // Get window size class for responsive layouts
                val windowSizeClass = calculateWindowSizeClass(this)
                // Create navigation controller
                val navController = rememberNavController()

                // Main app content
                AppContent(
                    windowSizeClass = windowSizeClass,
                    navController = navController
                )
            }
        }
    }
}

@Composable
private fun AppContent(
    windowSizeClass: androidx.compose.material3.windowsizeclass.WindowSizeClass,
    navController: androidx.navigation.NavHostController
) {
    MaterialTheme {
        AppNavigation(
            windowSizeClass = windowSizeClass,
            navController = navController
        )
    }
}