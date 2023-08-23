package com.jsmecommerce.portal3scanner.ui.components.general

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.jsmecommerce.portal3scanner.ui.theme.Color
import com.jsmecommerce.portal3scanner.utils.Auth
import com.jsmecommerce.portal3scanner.viewmodels.CoreViewModel
import com.jsmecommerce.portal3scanner.viewmodels.UiViewModel

@Composable
fun TopBar(
    uiViewModel: UiViewModel,
    coreViewModel: CoreViewModel
) {
    val context = LocalContext.current
    val auth = Auth(context)
    val user = auth.getUser()
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.Primary.Dark)
            .padding(horizontal = 16.dp)
            .height(20.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            modifier = Modifier.weight(1f),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Clock(uiViewModel = uiViewModel)
        }
        Row(
            modifier = Modifier.weight(1f),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            if(user != null)
                SimpleText(user.fullName)
        }
        Row(
            modifier = Modifier.weight(1f),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.End
        ) {
            BatteryStatus(coreViewModel = coreViewModel)
        }
    }
}