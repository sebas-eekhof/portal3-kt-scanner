package com.jsmecommerce.portal3scanner.ui.components.dashboard

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.jsmecommerce.portal3scanner.ui.components.general.SimpleText
import com.jsmecommerce.portal3scanner.ui.theme.Color

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardShortcut(@DrawableRes icon: Int, @StringRes name: Int, onClick: () -> Unit) {
    Surface(
        onClick = { onClick() },
        color = Color.Transparent,
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier
                .background(Color.Element, RoundedCornerShape(4))
                .fillMaxWidth()
                .padding(vertical = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(painter = painterResource(id = icon), contentDescription = stringResource(id = name), tint = Color.TextSecondary)
            Spacer(modifier = Modifier.height(8.dp))
            SimpleText(text = stringResource(id = name))
        }
    }
}