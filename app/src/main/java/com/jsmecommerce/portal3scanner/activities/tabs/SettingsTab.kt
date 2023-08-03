package com.jsmecommerce.portal3scanner.activities.tabs

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.jsmecommerce.portal3scanner.ui.components.settings.SettingsClickableItem
import com.jsmecommerce.portal3scanner.ui.components.settings.SettingsGroup
import com.jsmecommerce.portal3scanner.R
import com.jsmecommerce.portal3scanner.ui.components.settings.SettingsDivider

@Composable
fun SettingsTab(onLogout: () -> Unit) {
    Column(
        modifier = Modifier
            .verticalScroll(rememberScrollState())
            .padding(16.dp),

    ) {
        SettingsGroup(name = "Account", first = true) {
            SettingsClickableItem(
                name = "Wachtwoord wijzigen",
                icon = R.drawable.ic_password
            )
            SettingsDivider()
            SettingsClickableItem(
                name = "Uitloggen",
                icon = R.drawable.ic_logout,
                onClick = { onLogout() }
            )
        }
        SettingsGroup(name = "App") {
            SettingsClickableItem(
                name = "Meldingen",
                icon = R.drawable.ic_bell
            )
            SettingsDivider()
            SettingsClickableItem(
                name = "Informatie",
                icon = R.drawable.ic_info
            )
        }
    }
}