package com.jsmecommerce.portal3scanner.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowCompat
import com.google.accompanist.systemuicontroller.rememberSystemUiController

private val ColorScheme = darkColorScheme(
    primary = Color.Primary.Regular,
    secondary = Color.Element,
    tertiary = Color.Primary.Dark
)

@Composable
fun Portal3ScannerTheme(
    centerHorizontal: Boolean = false,
    centerVertical: Boolean = false,
    padding: Dp = 0.dp,
    content: @Composable () -> Unit
) {


    MaterialTheme(
        colorScheme = ColorScheme,
        typography = Typography,
        content = {
            val systemUiController = rememberSystemUiController()
            SideEffect {
                systemUiController.setStatusBarColor(
                    color = androidx.compose.ui.graphics.Color.Transparent,
                    darkIcons = false
                )
            }
            Surface(
                modifier = Modifier.fillMaxSize(),
                color = Color.Background
            ) {

            }

            Column(
                modifier = Modifier.systemBarsPadding().fillMaxSize().padding(padding),
                verticalArrangement = if (centerVertical) Arrangement.Center else Arrangement.Top,
                horizontalAlignment = if (centerHorizontal) Alignment.CenterHorizontally else Alignment.Start
            ) {
                content()
            }
        }
    )
}