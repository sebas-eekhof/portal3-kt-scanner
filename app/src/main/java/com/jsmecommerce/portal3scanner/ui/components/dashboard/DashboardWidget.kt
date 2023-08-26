package com.jsmecommerce.portal3scanner.ui.components.dashboard

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.jsmecommerce.portal3scanner.ui.components.general.Card
import com.jsmecommerce.portal3scanner.ui.components.general.Description
import com.jsmecommerce.portal3scanner.ui.components.general.SmallTitle
import com.jsmecommerce.portal3scanner.ui.theme.Color

@Composable
fun DashboardWidget(@DrawableRes icon: Int, @StringRes title: Int, value: String? = null, component: (@Composable () -> Unit)? = null) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(64.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxSize()
        ) {
            Icon(painter = painterResource(icon), contentDescription = stringResource(title), tint = Color.White)
            Spacer(modifier = Modifier.width(8.dp))
            Column {
                SmallTitle(stringResource(id = title))
                Spacer(modifier = Modifier.height(2.dp))
                if(value != null)
                    Description(value)
                else if(component != null)
                    component()
            }
        }
    }
}