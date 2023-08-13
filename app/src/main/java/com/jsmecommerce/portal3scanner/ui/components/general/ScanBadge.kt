package com.jsmecommerce.portal3scanner.ui.components.general

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.jsmecommerce.portal3scanner.ui.theme.Color
import com.jsmecommerce.portal3scanner.R

@Composable
fun ScanBadge(scanned: Int, total: Int) {
    Row(
        modifier = Modifier
            .background(if (scanned == total) Color.Success.Regular else Color.Primary.Regular, RoundedCornerShape(7)),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            modifier = Modifier
                .padding(2.dp)
                .height(24.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                modifier = Modifier
                    .background(Color.Element, RoundedCornerShape(7))
                    .height(24.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                SimpleTextBoldSmall(text = "$scanned / $total", modifier = Modifier.padding(horizontal = 4.dp))
            }
            Spacer(modifier = Modifier.width(2.dp))
            Icon(painter = painterResource(id = if (scanned == total) R.drawable.ic_check else R.drawable.ic_scan), contentDescription = "Barcode", tint = Color.White, modifier = Modifier.size(16.dp))
        }
    }
}