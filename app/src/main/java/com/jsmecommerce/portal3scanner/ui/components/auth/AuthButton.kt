package com.jsmecommerce.portal3scanner.ui.components.auth

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp
import com.jsmecommerce.portal3scanner.ui.components.SimpleText
import com.jsmecommerce.portal3scanner.ui.theme.Color

@Composable
fun AuthButton(text: String, onClick: () -> Unit, loading: Boolean = false) {
    Button(
        onClick,
        modifier = Modifier
            .fillMaxWidth()
            .height(48.dp)
            .background(
                color = Color.Primary.Regular,
                shape = RoundedCornerShape(6.dp)
            ),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.Transparent
        ),
        enabled = !loading
    ) {
        if(loading)
            CircularProgressIndicator(
                color = Color.White,
                modifier = Modifier.size(16.dp),
                strokeWidth = 2.dp
            )
        else
            SimpleText(text)
    }
}