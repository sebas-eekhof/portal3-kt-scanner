package com.jsmecommerce.portal3scanner.activities.tabs.SettingsTab

import android.os.Build
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.jsmecommerce.portal3scanner.BuildConfig
import com.jsmecommerce.portal3scanner.ui.components.info.InfoGroup
import com.jsmecommerce.portal3scanner.ui.components.info.InfoItem
import com.jsmecommerce.portal3scanner.ui.components.settings.SettingsDivider
import com.jsmecommerce.portal3scanner.utils.AppController
import com.jsmecommerce.portal3scanner.utils.Device

@Composable
fun SettingsInformation(appController: AppController) {
    appController.setPage("Informatie", backRoute = "overview")

    Column(
        modifier = Modifier
            .verticalScroll(rememberScrollState())
            .padding(16.dp),

    ) {
        InfoGroup(name = "Installatie", first = true) {
            InfoItem(name = "Versie", value = BuildConfig.VERSION_NAME)
            SettingsDivider()
            InfoItem(name = "Build", value = BuildConfig.VERSION_CODE.toString())
        }
        InfoGroup(name = "Software") {
            InfoItem(name = "Android versie", value = Build.VERSION.RELEASE)
            SettingsDivider()
            InfoItem(name = "SDK versie", value = Build.VERSION.SDK_INT.toString())
        }
        InfoGroup(name = "Hardware") {
            InfoItem(name = "Apparaat", value = "${Build.MANUFACTURER} ${Build.MODEL}")
            SettingsDivider()
            InfoItem(name = "Cores", value = Device().getCores().toString())
            SettingsDivider()
            InfoItem(name = "Memory", value = Device().getMemoryGB())
        }
    }
}