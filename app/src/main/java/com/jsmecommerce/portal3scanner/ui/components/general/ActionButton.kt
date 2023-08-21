package com.jsmecommerce.portal3scanner.ui.components.general

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.jsmecommerce.portal3scanner.ui.theme.Color

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ActionButton(
    @DrawableRes icon: Int,
    onClick: () -> Unit
) {
    Surface(
        color = Color.Transparent,
        onClick = { onClick() }
    ) {
        Icon(
            painter = painterResource(id = icon),
            contentDescription = "",
            modifier = Modifier
                .padding(6.dp)
                .size(16.dp),
            tint = Color.White
        )
    }
}