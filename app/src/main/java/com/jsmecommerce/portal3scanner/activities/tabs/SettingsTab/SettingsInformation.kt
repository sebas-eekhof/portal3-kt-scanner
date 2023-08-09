package com.jsmecommerce.portal3scanner.activities.tabs.SettingsTab

import android.os.Build
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.jsmecommerce.portal3scanner.BuildConfig
import com.jsmecommerce.portal3scanner.R
import com.jsmecommerce.portal3scanner.ui.components.general.ScannerHost
import com.jsmecommerce.portal3scanner.ui.components.info.InfoGroup
import com.jsmecommerce.portal3scanner.ui.components.info.InfoItem
import com.jsmecommerce.portal3scanner.ui.components.settings.SettingsDivider
import com.jsmecommerce.portal3scanner.utils.Auth
import com.jsmecommerce.portal3scanner.utils.Device
import com.jsmecommerce.portal3scanner.utils.humanDate
import com.jsmecommerce.portal3scanner.viewmodels.MainViewModel

@Composable
fun SettingsInformation(nav: NavHostController, mvm: MainViewModel) {
    val context = LocalContext.current
    val user = Auth(context).getUser()

    LaunchedEffect(Unit) {
        mvm.init(
            title = context.getString(R.string.settings_information_title)
        )
    }

    ScannerHost(nav = nav)

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
        InfoGroup(name = "Sessie") {
            InfoItem(name = "ID", value = user?.id.toString() ?: "")
            SettingsDivider()
            InfoItem(name = "Email", value = user?.email ?: "")
            SettingsDivider()
            InfoItem(name = "Naam", value = user?.fullName ?: "")
            SettingsDivider()
            InfoItem(name = "Geldig tot", value = if (user != null) humanDate(user.expire, context) else "")
        }
        InfoGroup(name = "Software") {
            InfoItem(name = "Android versie", value = Build.VERSION.RELEASE)
            SettingsDivider()
            InfoItem(name = "SDK versie", value = Build.VERSION.SDK_INT.toString())
        }
        InfoGroup(name = "Hardware") {
            InfoItem(name = "Apparaat", value = "${Build.MANUFACTURER} ${Build.MODEL}")
            SettingsDivider()
            InfoItem(name = "MAC", value = Device().getMAC())
            SettingsDivider()
            InfoItem(name = "Cores", value = Device().getCores().toString())
            SettingsDivider()
            InfoItem(name = "Memory", value = Device().getMemoryGB())
        }
    }
}