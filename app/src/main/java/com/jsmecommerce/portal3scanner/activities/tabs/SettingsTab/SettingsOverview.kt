package com.jsmecommerce.portal3scanner.activities.tabs.SettingsTab

import android.app.Activity
import android.content.Intent
import android.provider.Settings
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
import com.jsmecommerce.portal3scanner.R
import com.jsmecommerce.portal3scanner.activities.AuthActivity
import com.jsmecommerce.portal3scanner.ui.components.general.ScannerHost
import com.jsmecommerce.portal3scanner.ui.components.general.UserBanner
import com.jsmecommerce.portal3scanner.ui.components.screens.TutorialScreen
import com.jsmecommerce.portal3scanner.ui.components.settings.SettingsClickableItem
import com.jsmecommerce.portal3scanner.ui.components.settings.SettingsDivider
import com.jsmecommerce.portal3scanner.ui.components.settings.SettingsGroup
import com.jsmecommerce.portal3scanner.utils.Auth
import com.jsmecommerce.portal3scanner.viewmodels.MainViewModel

@Composable
fun SettingsOverview(nav: NavHostController, mvm: MainViewModel) {
    val context = LocalContext.current
    val auth = Auth(context)
    val user = auth.getUser()!!

    fun logout() {
        auth.logout()
        val activity = (context as? Activity)
        context.startActivity(Intent(context, AuthActivity::class.java))
        activity?.finish()
    }

    LaunchedEffect(Unit) {
        mvm.init(
            title = context.getString(R.string.settings_title),
            disableBack = true
        )
    }
    
    ScannerHost(nav = nav)

    Column(
        modifier = Modifier
            .verticalScroll(rememberScrollState())
            .padding(16.dp),

    ) {
        UserBanner(user = user)

        SettingsGroup(name = R.string.settings_cat_account) {
            SettingsClickableItem(
                name = R.string.settings_cat_account_change_password,
                icon = R.drawable.ic_password
            )
            SettingsDivider()
            SettingsClickableItem(
                name = R.string.settings_cat_account_sign_out,
                icon = R.drawable.ic_logout,
                onClick = { logout() }
            )
        }
        SettingsGroup(name = R.string.settings_cat_app) {
            SettingsClickableItem(
                name = R.string.settings_cat_app_tutorial,
                icon = R.drawable.ic_info,
                onClick = { mvm.setPopup { TutorialScreen { mvm.setPopup(null) } } }
            )
            SettingsDivider()
            SettingsClickableItem(
                name = R.string.settings_cat_app_notifications,
                icon = R.drawable.ic_bell
            )
            SettingsDivider()
            SettingsClickableItem(
                name = R.string.settings_cat_app_information,
                icon = R.drawable.ic_info,
                onClick = { nav.navigate("settings/information") }
            )
        }
        SettingsGroup(name = R.string.settings_cat_device) {
            SettingsClickableItem(
                name = R.string.settings_cat_device_wifi,
                icon = R.drawable.ic_wifi,
                onClick = { context.startActivity(Intent(Settings.ACTION_WIFI_SETTINGS).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)) }
            )
            SettingsDivider()
            SettingsClickableItem(
                name = R.string.settings_cat_device_display,
                icon = R.drawable.ic_phone,
                onClick = { context.startActivity(Intent(Settings.ACTION_DISPLAY_SETTINGS).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)) }
            )
            SettingsDivider()
            SettingsClickableItem(
                name = R.string.settings_cat_device_sound,
                icon = R.drawable.ic_volume,
                onClick = { context.startActivity(Intent(Settings.ACTION_SOUND_SETTINGS).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)) }
            )
            SettingsDivider()
            SettingsClickableItem(
                name = R.string.settings_cat_device_security,
                icon = R.drawable.ic_lock,
                onClick = { context.startActivity(Intent(Settings.ACTION_SECURITY_SETTINGS).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)) }
            )
            SettingsDivider()
            SettingsClickableItem(
                name = R.string.settings_cat_device_scanner,
                icon = R.drawable.ic_scan,
                onClick = { nav.navigate("settings/scanner") }
            )
            SettingsDivider()
            SettingsClickableItem(
                name = R.string.settings_cat_device_about,
                icon = R.drawable.ic_info,
                onClick = { context.startActivity(Intent(Settings.ACTION_DEVICE_INFO_SETTINGS).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)) }
            )
        }
    }
}