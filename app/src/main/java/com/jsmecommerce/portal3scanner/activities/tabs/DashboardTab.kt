package com.jsmecommerce.portal3scanner.activities.tabs

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import com.jsmecommerce.portal3scanner.ui.components.general.SimpleText
import com.jsmecommerce.portal3scanner.utils.AppController

@Composable
fun DashboardTab(appController: AppController) {
    appController.setPage("Dashboard")

    Column {
        SimpleText("Dashboard")
    }
}