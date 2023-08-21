package com.jsmecommerce.portal3scanner.models

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import com.jsmecommerce.portal3scanner.ui.theme.Color

class Popup(
    val content: @Composable () -> Unit,
    val backdrop: Boolean = true,
    val onClose: (() -> Unit)? = null
) {
    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun Render(doClose: () -> Unit) {
        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {
            if(backdrop)
                Surface(
                    modifier = Modifier
                        .fillMaxSize()
                        .alpha(0.7f),
                    color = Color.Black,
                    onClick = {
                        doClose()
                        onClose?.let { it() }
                    }
                ) {

                }
            content()
        }
    }
}