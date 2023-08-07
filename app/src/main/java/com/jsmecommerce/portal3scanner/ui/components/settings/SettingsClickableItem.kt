package com.jsmecommerce.portal3scanner.ui.components.settings

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.jsmecommerce.portal3scanner.ui.components.general.SimpleText

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsClickableItem(name: String, @DrawableRes icon: Int, onClick: (() -> Unit)? = null) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .height(48.dp)
            .padding(0.dp)
            .defaultMinSize(0.dp),
        onClick = { if (onClick != null) onClick() },
        color = Color.Transparent
    ) {
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(16.dp)
        ) {
            Icon(painter = painterResource(id = icon), contentDescription = name, modifier = Modifier.size(20.dp), tint = com.jsmecommerce.portal3scanner.ui.theme.Color.TextSecondary)
            Spacer(modifier = Modifier.width(16.dp))
            SimpleText(name)
            Spacer(modifier = Modifier.weight(1f))
            Icon(imageVector = Icons.Default.KeyboardArrowRight, contentDescription = name, tint = com.jsmecommerce.portal3scanner.ui.theme.Color.TextSecondary)
        }
    }
}