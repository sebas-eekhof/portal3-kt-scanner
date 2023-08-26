package com.jsmecommerce.portal3scanner.ui.components.general

import androidx.annotation.DrawableRes
import androidx.compose.animation.core.animateOffsetAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.jsmecommerce.portal3scanner.R
import com.jsmecommerce.portal3scanner.ui.theme.Color

@Composable
fun Toast(
    message: String,
    @DrawableRes icon: Int = R.drawable.ic_action_name,
    onFinish: () -> Unit
) {
    var visible by remember { mutableStateOf(false) }
    val offset by animateOffsetAsState(targetValue = Offset(0f, if (visible) 0f else -20f), label = "Offset toast")

    LaunchedEffect(Unit) {
        visible = true
    }

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ) {
        Surface(
            color = Color.Element,
            shadowElevation = 60.dp,
            shape = RoundedCornerShape(100)
        ) {
            Row(
                modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 8.dp, bottom = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(painter = painterResource(id = icon), contentDescription = message, tint = Color.White, modifier = Modifier.size(16.dp))
                Spacer(modifier = Modifier.width(16.dp))
                SimpleTextLarge(message)
            }
        }
    }
}