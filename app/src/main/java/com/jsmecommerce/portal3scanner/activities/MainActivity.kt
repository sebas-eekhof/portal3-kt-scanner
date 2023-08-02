package com.jsmecommerce.portal3scanner.activities

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Text
import com.jsmecommerce.portal3scanner.ui.theme.Portal3ScannerTheme
import com.jsmecommerce.portal3scanner.utils.Database

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val db = Database(applicationContext)
        if(!db.has("jwt")) {
            startActivity(Intent(
                this,
                AuthActivity::class.java
            ))
            finish()
            return
        }

        setContent {
            Portal3ScannerTheme {
                Text(text = "Main layout")
            }
        }
    }
}