package com.jsmecommerce.portal3scanner.ui.components.info

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.jsmecommerce.portal3scanner.ui.components.general.Description
import com.jsmecommerce.portal3scanner.ui.theme.Color

@Composable
fun InfoGroup(name: String, first: Boolean = false, content: @Composable () -> Unit) {
    Column(
        modifier = Modifier
            .padding(top = if (first) 0.dp else 16.dp)
    ) {
        Description(name)
        Spacer(Modifier.height(16.dp))
        Surface(
            color = Color.Subelement,
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(4)
        ) {
            Column() {
                content()
            }
        }
    }
}