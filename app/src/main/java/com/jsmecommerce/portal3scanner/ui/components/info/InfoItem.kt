package com.jsmecommerce.portal3scanner.ui.components.info

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.jsmecommerce.portal3scanner.ui.components.general.SimpleText
import com.jsmecommerce.portal3scanner.ui.components.general.SimpleTextBold

@Composable
fun InfoItem(name: String, value: String) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .height(48.dp)
            .padding(0.dp)
            .defaultMinSize(0.dp),
        color = Color.Transparent
    ) {
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(16.dp)
        ) {
            SimpleTextBold(name)
            Spacer(modifier = Modifier.weight(1f))
            SimpleText(value)
        }
    }
}