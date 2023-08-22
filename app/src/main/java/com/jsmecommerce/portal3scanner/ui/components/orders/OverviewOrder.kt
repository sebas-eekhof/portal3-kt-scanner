package com.jsmecommerce.portal3scanner.ui.components.orders

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.jsmecommerce.portal3scanner.R
import com.jsmecommerce.portal3scanner.models.orders.OverviewOrder
import com.jsmecommerce.portal3scanner.ui.components.general.Description
import com.jsmecommerce.portal3scanner.ui.components.general.DotText
import com.jsmecommerce.portal3scanner.ui.components.general.Jdenticon
import com.jsmecommerce.portal3scanner.ui.components.general.SimpleText
import com.jsmecommerce.portal3scanner.ui.components.general.SmallTitle
import com.jsmecommerce.portal3scanner.ui.theme.Color
import com.jsmecommerce.portal3scanner.utils.Formatter

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun OverviewOrder(order: OverviewOrder, onClick: () -> Unit) {
    val context = LocalContext.current

    Surface(
        color = Color.Element,
        shape = RoundedCornerShape(4.dp),
        modifier = Modifier
            .fillMaxWidth()
            .combinedClickable(
                onClick = {
                    onClick()
                },
                onLongClick = {
                    onClick()
                }
            )
    ) {
        Row(
            modifier = Modifier.padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                SmallTitle(order.ordernumber_full)
                Spacer(modifier = Modifier.height(6.dp))
                Description(Formatter.humanDate(order.created_at, context))
                Spacer(modifier = Modifier.height(6.dp))
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Jdenticon(order.customer.id.toString(), size = 32)
                    Spacer(modifier = Modifier.width(6.dp))
                    Description(order.customer.delivery_address?.full_name ?: "Onbekende klant")
                }
            }
            Row(
                horizontalArrangement = Arrangement.End,
                modifier = Modifier.fillMaxWidth(1f)
            ) {
                Column(
                    horizontalAlignment = Alignment.End
                ) {
                    DotText(text = order.store.name, color = order.store.color)
                    Spacer(modifier = Modifier.height(6.dp))
                    SimpleText(
                        text = stringResource(id = if (order.rules.in_stock) R.string.orders_in_stock else R.string.orders_out_of_stock),
                        color = if (order.rules.in_stock) Color.Success.Regular else Color.Danger.Regular
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    DotText(text = order.status.name, color = order.status.color)
                }
            }
        }
    }
}