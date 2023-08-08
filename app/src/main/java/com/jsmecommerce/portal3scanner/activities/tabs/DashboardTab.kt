package com.jsmecommerce.portal3scanner.activities.tabs

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import com.jsmecommerce.portal3scanner.R
import com.jsmecommerce.portal3scanner.ui.components.general.ScannerHost
import com.jsmecommerce.portal3scanner.ui.components.general.SimpleText
import com.jsmecommerce.portal3scanner.viewmodels.MainViewModel

@Composable
fun DashboardTab(nav: NavHostController, mvm: MainViewModel) {
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        mvm.setTitle(context.getString(R.string.dashboard_title))
        mvm.disableBack()
    }

    ScannerHost()

    Column {
        SimpleText("Dashboard")
    }
}