package com.jsmecommerce.portal3scanner.activities

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Text
import com.jsmecommerce.portal3scanner.ui.theme.Portal3ScannerTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Portal3ScannerTheme {
                Text(text = "Main layout")
            }
        }
    }
}