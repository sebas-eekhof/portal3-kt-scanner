package com.jsmecommerce.portal3scanner.activities

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.jsmecommerce.portal3scanner.ui.components.general.Jdenticon
import com.jsmecommerce.portal3scanner.ui.components.general.Title
import com.jsmecommerce.portal3scanner.ui.theme.Portal3ScannerTheme
import com.jsmecommerce.portal3scanner.utils.Auth

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val auth = Auth(applicationContext)

        val user = auth.getUser()

        if(user == null) {
            startActivity(Intent(this, AuthActivity::class.java))
            finish()
            return
        }

        setContent {
            Portal3ScannerTheme {
                Jdenticon(jdenticon = user.jdenticon)
                Title(text = "Welkom terug ${user.fullName}")
                
            }
        }
    }
}