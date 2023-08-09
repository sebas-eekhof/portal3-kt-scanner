package com.jsmecommerce.portal3scanner.activities.tabs

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.jsmecommerce.portal3scanner.R
import com.jsmecommerce.portal3scanner.models.DashboardShortcutHolder
import com.jsmecommerce.portal3scanner.ui.components.dashboard.DashboardShortcut
import com.jsmecommerce.portal3scanner.ui.components.general.ScannerHost
import com.jsmecommerce.portal3scanner.ui.components.general.UserBanner
import com.jsmecommerce.portal3scanner.ui.components.screens.TutorialScreen
import com.jsmecommerce.portal3scanner.utils.Auth
import com.jsmecommerce.portal3scanner.viewmodels.MainViewModel
@Composable
fun DashboardTab(nav: NavHostController, mvm: MainViewModel) {
    val context = LocalContext.current
    val auth = Auth(context)
    val user = auth.getUser()!!
    val shortcuts = listOf(
        DashboardShortcutHolder(icon = R.drawable.ic_info, name = R.string.settings_cat_app_tutorial, onClick = { mvm.setPopup { TutorialScreen { mvm.setPopup(null) } } }),
        DashboardShortcutHolder(icon = R.drawable.ic_orders, name = R.string.orders_title, onClick = { nav.navigate("orders/overview") }),
        DashboardShortcutHolder(icon = R.drawable.ic_settings, name = R.string.settings_title, onClick = { nav.navigate("settings/overview") }),
    )

    LaunchedEffect(Unit) {
        mvm.init(
            title = context.getString(R.string.dashboard_title),
            disableBack = true
        )
    }

    ScannerHost(nav = nav)

    Column {
        Column(
            modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 16.dp)
        ) {
            UserBanner(user = user)
        }
        LazyVerticalGrid(
            columns = GridCells.Adaptive(minSize = 128.dp),
            contentPadding = PaddingValues(
                start = 12.dp,
                top = 4.dp,
                end = 12.dp,
                bottom = 0.dp
            ),
            content = {
                items(items = shortcuts) {
                    Column(
                        modifier = Modifier
                            .padding(4.dp)
                            .fillMaxWidth()
                    ) {
                        DashboardShortcut(icon = it.icon, name = it.name, onClick = it.onClick)
                    }
                }
            }
        )
    }
}