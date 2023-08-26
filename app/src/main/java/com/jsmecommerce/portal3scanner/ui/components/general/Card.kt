package com.jsmecommerce.portal3scanner.ui.components.general

import android.content.Context
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.jsmecommerce.portal3scanner.ui.theme.Color

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Card(
    modifier: Modifier = Modifier.fillMaxWidth(),
    title: String? = null,
    onClick: (Context.() -> Unit)? = null,
    noPadding: Boolean = false,
    content: @Composable () -> Unit
) {
    val context = LocalContext.current
    Column {
        if(title != null) {
            SmallTitle(text = title)
            Spacer(modifier = Modifier.height(8.dp))
        }
        Surface(
            color = Color.Element,
            shape = RoundedCornerShape(12.dp),
            modifier = modifier,
            shadowElevation = 8.dp,
            onClick = { onClick?.let { with(context) { it() } } }
        ) {
            Column(
                modifier = Modifier
                    .padding(if (noPadding) 0.dp else 8.dp)
                    .fillMaxWidth()
            ) {
                content()
            }
        }
    }
}