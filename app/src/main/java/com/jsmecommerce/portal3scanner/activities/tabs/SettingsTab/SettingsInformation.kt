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
import androidx.compose.ui.res.stringResource
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
import com.jsmecommerce.portal3scanner.utils.Formatter
import com.jsmecommerce.portal3scanner.viewmodels.CoreViewModel
import com.jsmecommerce.portal3scanner.viewmodels.UiViewModel

@Composable
fun SettingsInformation(nav: NavHostController, coreViewModel: CoreViewModel, uiViewModel: UiViewModel) {
    val context = LocalContext.current
    val user = Auth(context).getUser()
    val device = Device(context)

    LaunchedEffect(Unit) {
        uiViewModel.init(
            title = context.getString(R.string.settings_information_title)
        )
    }

    ScannerHost(nav = nav)

    Column(
        modifier = Modifier
            .verticalScroll(rememberScrollState())
            .padding(16.dp),

    ) {
        InfoGroup(name = stringResource(id = R.string.settings_information_installation), first = true) {
            InfoItem(name = stringResource(id = R.string.version), value = BuildConfig.VERSION_NAME)
            SettingsDivider()
            InfoItem(name = stringResource(id = R.string.build), value = BuildConfig.VERSION_CODE.toString())
        }
        InfoGroup(name = stringResource(id = R.string.settings_information_session)) {
            InfoItem(name = stringResource(id = R.string.settings_information_session_id), value = user?.id.toString() ?: "")
            SettingsDivider()
            InfoItem(name = stringResource(id = R.string.settings_information_session_email), value = user?.email ?: "")
            SettingsDivider()
            InfoItem(name = stringResource(id = R.string.settings_information_session_name), value = user?.fullName ?: "")
            SettingsDivider()
            InfoItem(name = stringResource(id = R.string.settings_information_session_valid_till), value = if (user != null) Formatter.humanDate(user.expire, context) else "")
        }
        InfoGroup(name = stringResource(id = R.string.settings_information_software)) {
            InfoItem(name = stringResource(id = R.string.settings_information_software_android_version), value = Build.VERSION.RELEASE)
            SettingsDivider()
            InfoItem(name = stringResource(id = R.string.settings_information_software_sdk_version), value = Build.VERSION.SDK_INT.toString())
        }
        InfoGroup(name = stringResource(id = R.string.settings_information_hardware)) {
            InfoItem(name = stringResource(id = R.string.settings_information_hardware_device), value = "${Build.MANUFACTURER} ${Build.MODEL}")
            SettingsDivider()
            InfoItem(name = stringResource(id = R.string.settings_information_hardware_mac), value = Device.getMAC() ?: "02:00:00:00:00:00")
            SettingsDivider()
            InfoItem(name = stringResource(id = R.string.settings_information_hardware_hwid), value = device.getHWID())
            SettingsDivider()
            InfoItem(name = stringResource(id = R.string.settings_information_hardware_cpu_cores), value = Device.getCores().toString())
            SettingsDivider()
            InfoItem(name = stringResource(id = R.string.settings_information_hardware_memory), value = Device.getMemoryGB())
        }
    }
}