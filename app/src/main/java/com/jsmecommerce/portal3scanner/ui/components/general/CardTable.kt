package com.jsmecommerce.portal3scanner.ui.components.general

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.unit.dp
import com.jsmecommerce.portal3scanner.models.TableRow
import com.jsmecommerce.portal3scanner.ui.theme.Color

@Composable
fun CardTable(title: String?, rows: List<TableRow> = listOf()) {
    Column {
        if(title != null) {
            SmallTitle(title)
            Spacer(modifier = Modifier.height(8.dp))
        }
        Column(
            modifier = Modifier
                .background(Color.Element, RoundedCornerShape(4.dp))
                .padding(8.dp)
        ) {
            for(i in 0 until rows.count()) {
                val row = rows[i]
                if(i != 0) {
                    Spacer(modifier = Modifier.height(8.dp))
                    Divider(color = Color.TextSecondary, modifier = Modifier.alpha(0.2f))
                    Spacer(modifier = Modifier.height(8.dp))
                }
                Row {
                    SimpleTextBold(row.name)
                    Spacer(modifier = Modifier.weight(1f))
                    if(row.component != null)
                        row.component!!()
                    else if(row.value != null)
                        Description(row.value)
                }
            }
        }
    }
}