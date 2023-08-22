package com.jsmecommerce.portal3scanner.ui.components.popups

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.jsmecommerce.portal3scanner.R
import com.jsmecommerce.portal3scanner.activities.MainActivity
import com.jsmecommerce.portal3scanner.ui.components.secretmenu.SecretMenuItem
import com.jsmecommerce.portal3scanner.ui.theme.Color
import com.jsmecommerce.portal3scanner.utils.Auth

@Composable
fun SecretMenuPopup(activity: MainActivity) {
    Surface(
        color = Color.Element,
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(4)
    ) {
        Column {
            SecretMenuItem(name = R.string.secret_menu_close_app, icon = R.drawable.ic_close) { activity.finish() }
            SecretMenuItem(name = R.string.settings_cat_account_sign_out, icon = R.drawable.ic_logout) { Auth(activity.applicationContext).logout() }
        }
    }
}