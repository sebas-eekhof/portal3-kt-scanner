package com.jsmecommerce.portal3scanner.ui.components.orders

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.unit.dp
import com.jsmecommerce.portal3scanner.datasource.portal3api.models.orders.OrderRule
import com.jsmecommerce.portal3scanner.ui.components.general.Badge
import com.jsmecommerce.portal3scanner.ui.components.general.CdnImage
import com.jsmecommerce.portal3scanner.ui.components.general.Description
import com.jsmecommerce.portal3scanner.ui.components.general.DotText
import com.jsmecommerce.portal3scanner.ui.components.general.ScanBadge
import com.jsmecommerce.portal3scanner.ui.components.general.SmallTitle
import com.jsmecommerce.portal3scanner.ui.theme.Color

@Composable
fun OrderRule(rule: OrderRule, rules: List<OrderRule>) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.Element, RoundedCornerShape(4))
    ) {
        Row(
            modifier = Modifier
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            if(rule.product != null) {
                CdnImage(path = "/products/${rule.product.id}")
                Spacer(modifier = Modifier.width(16.dp))
            }
            Column {
                SmallTitle(rule.title)
                if(rule.product != null) {
                    if(rule.title != rule.product.name) {
                        Spacer(modifier = Modifier.height(4.dp))
                        Description("(${rule.product.name})")
                    }
                    Spacer(modifier = Modifier.height(4.dp))
                    DotText(text = rule.product.category.name, color = rule.product.category.color)
                }
            }
        }
        Divider(color = Color.TextSecondary, modifier = Modifier.alpha(0.3f))
        Row(
            modifier = Modifier
                .padding(8.dp)
        ) {
            Badge(color = if (rule.stock < (rule.quantity - rule.scansAmount)) Color.Danger.Regular else Color.Success.Regular, text = "${rule.stock} in voorraad")
            Spacer(modifier = Modifier.width(8.dp))
            ScanBadge(scanned = rule.scansAmount, total = rule.quantity)
        }
    }
}