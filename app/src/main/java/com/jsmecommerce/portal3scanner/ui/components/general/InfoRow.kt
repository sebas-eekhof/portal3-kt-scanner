package com.jsmecommerce.portal3scanner.ui.components.general

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.jsmecommerce.portal3scanner.ui.theme.Color

@Composable
fun InfoRow(name: String, value: String? = null, component: (@Composable () -> Unit)? = null) {
    Row(
        modifier = Modifier
            .background(Color.Element, RoundedCornerShape(10))
            .padding(8.dp),
        horizontalArrangement = Arrangement.Center
    ) {
        SimpleTextBold(name)
        Spacer(modifier = Modifier.weight(1f))
        if(component != null)
            component()
        else if(value != null)
            Description(value)
    }
}