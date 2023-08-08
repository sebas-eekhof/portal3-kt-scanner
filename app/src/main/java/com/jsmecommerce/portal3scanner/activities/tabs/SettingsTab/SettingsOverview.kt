package com.jsmecommerce.portal3scanner.activities.tabs.SettingsTab

import android.content.Intent
import android.provider.Settings
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
import com.jsmecommerce.portal3scanner.activities.MainActivity
import com.jsmecommerce.portal3scanner.stores.NavigationStore
import com.jsmecommerce.portal3scanner.ui.components.settings.SettingsDivider

@Composable
fun SettingsOverview(activity: MainActivity) {
    val navigationStore = NavigationStore()
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
                icon = R.drawable.ic_logout
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
                icon = R.drawable.ic_info,
                onClick = { navigationStore.navigate("information") }
            )
        }
        SettingsGroup(name = "Apparaat") {
            SettingsClickableItem(
                name = "WiFi",
                icon = R.drawable.ic_bell,
                onClick = { activity.startActivity(Intent(Settings.ACTION_WIFI_SETTINGS).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)) }
            )
            SettingsDivider()
            SettingsClickableItem(
                name = "Beeldscherm",
                icon = R.drawable.ic_info,
                onClick = { activity.startActivity(Intent(Settings.ACTION_DISPLAY_SETTINGS).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)) }
            )
            SettingsDivider()
            SettingsClickableItem(
                name = "Geluid",
                icon = R.drawable.ic_info,
                onClick = { activity.startActivity(Intent(Settings.ACTION_SOUND_SETTINGS).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)) }
            )
            SettingsDivider()
            SettingsClickableItem(
                name = "Beveiliging",
                icon = R.drawable.ic_info,
                onClick = { activity.startActivity(Intent(Settings.ACTION_SECURITY_SETTINGS).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)) }
            )
            SettingsDivider()
            SettingsClickableItem(
                name = "Over",
                icon = R.drawable.ic_info,
                onClick = { activity.startActivity(Intent(Settings.ACTION_DEVICE_INFO_SETTINGS).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)) }
            )
        }
    }
}