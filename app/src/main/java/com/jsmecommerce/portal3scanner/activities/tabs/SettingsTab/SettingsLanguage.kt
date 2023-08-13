package com.jsmecommerce.portal3scanner.activities.tabs.SettingsTab

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.jsmecommerce.portal3scanner.ui.components.settings.SettingsGroup
import com.jsmecommerce.portal3scanner.utils.Static
import com.jsmecommerce.portal3scanner.viewmodels.MainViewModel
import com.jsmecommerce.portal3scanner.R
import com.jsmecommerce.portal3scanner.ui.components.settings.SettingsClickableItem
import com.jsmecommerce.portal3scanner.ui.components.settings.SettingsDivider
import java.util.Locale

@Composable
fun SettingsLanguage(nav: NavController, mvm: MainViewModel) {
    val context = LocalContext.current

    fun setLocale(locale: Locale) {
        Locale.setDefault(locale)
        var config = context.resources.configuration
        config.setLocale(locale)
        context.createConfigurationContext(config)
    }

    Column(
        modifier = Modifier.padding(16.dp)
    ) {
        SettingsGroup(name = R.string.settings_cat_app_language, first = true) {
            for(i in 0 until Static.locales.count()) {
                if(i != 0)
                    SettingsDivider()
                SettingsClickableItem(name = Static.locales[i].displayName, onClick = { setLocale(Static.locales[i]) })
            }
        }
    }
}