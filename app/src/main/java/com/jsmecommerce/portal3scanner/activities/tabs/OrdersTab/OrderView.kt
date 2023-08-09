package com.jsmecommerce.portal3scanner.activities.tabs.OrdersTab

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.jsmecommerce.portal3scanner.ui.components.general.Description
import com.jsmecommerce.portal3scanner.viewmodels.MainViewModel

@Composable
fun OrderView(nav: NavHostController, mvm: MainViewModel, orderId: Int, title: String) {
    LaunchedEffect(Unit) {
        mvm.init(
            title = title
        )
    }
    
    Column(
        modifier = Modifier
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {
        Description(text = "Now viewing order $title ($orderId)")
    }
}