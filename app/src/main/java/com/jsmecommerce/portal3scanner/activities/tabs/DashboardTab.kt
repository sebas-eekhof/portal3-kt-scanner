package com.jsmecommerce.portal3scanner.activities.tabs

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.jsmecommerce.portal3scanner.R
import com.jsmecommerce.portal3scanner.models.DashboardShortcutHolder
import com.jsmecommerce.portal3scanner.ui.components.dashboard.DashboardBatteryWidget
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

    LaunchedEffect(Unit) {
        mvm.init(
            title = context.getString(R.string.dashboard_title),
            disableBack = true
        )
    }

    ScannerHost(nav = nav)

    Column(
        modifier = Modifier.padding(16.dp)
    ) {
        UserBanner(user = user)
        Spacer(modifier = Modifier.height(8.dp))
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(Modifier.weight(1f)) { DashboardBatteryWidget(mvm = mvm) }
            Spacer(modifier = Modifier.width(8.dp))
            Column(Modifier.weight(1f)) { DashboardBatteryWidget(mvm = mvm) }
        }
//        LazyVerticalGrid(
//            columns = GridCells.Adaptive(minSize = 128.dp),
//            contentPadding = PaddingValues(
//                start = 12.dp,
//                top = 4.dp,
//                end = 12.dp,
//                bottom = 0.dp
//            ),
//            content = {
//                items(items = shortcuts) {
//                    Column(
//                        modifier = Modifier
//                            .padding(4.dp)
//                            .fillMaxWidth()
//                    ) {
//                        DashboardShortcut(icon = it.icon, name = it.name, onClick = it.onClick)
//                    }
//                }
//            }
//        )
    }
}