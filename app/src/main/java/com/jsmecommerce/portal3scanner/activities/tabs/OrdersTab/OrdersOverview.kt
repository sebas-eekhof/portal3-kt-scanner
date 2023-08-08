package com.jsmecommerce.portal3scanner.activities.tabs.OrdersTab

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
import com.jsmecommerce.portal3scanner.enums.ColorEnum
import com.jsmecommerce.portal3scanner.models.general.Address
import com.jsmecommerce.portal3scanner.models.general.Customer
import com.jsmecommerce.portal3scanner.models.general.OverviewStore
import com.jsmecommerce.portal3scanner.models.orders.OrderStatus
import com.jsmecommerce.portal3scanner.models.orders.OverviewOrder
import com.jsmecommerce.portal3scanner.ui.components.orders.OverviewOrder
import com.jsmecommerce.portal3scanner.viewmodels.MainViewModel
import com.jsmecommerce.portal3scanner.R

@Composable
fun OrdersOverview(nav: NavHostController, mvm: MainViewModel) {
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        mvm.setTitle(context.getString(R.string.orders_title))
        mvm.disableBack()
    }

    val orders = listOf(
        OverviewOrder(
            id = 1,
            ordernumber = 202306031,
            ordernumber_full = "K-202306031",
            is_concept = false,
            is_paid = true,
            store = OverviewStore(1, "Kwekenmetled.nl (LSE)", ColorEnum.yellow),
            status = OrderStatus(1, "Nieuw", ColorEnum.blue, "new"),
            rules = OverviewOrder.OverviewOrderRules(12, true),
            tags = listOf(),
            customer = Customer(
                17587,
                Customer.CustomerType.private,
                admin_address = Address(
                    "Krystian Chomko",
                    "krystiankrs1@gmail.com",
                    "+32 467822396",
                    "Rue Vanderstichelen",
                    "1080",
                    "74",
                    "Brussels",
                    null,
                    "BE"
                ),
                delivery_address = Address(
                    "Krystian Chomko",
                    "krystiankrs1@gmail.com",
                    "+32 467822396",
                    "Rue Vanderstichelen",
                    "1080",
                    "74",
                    "Brussels",
                    null,
                    "BE"
                )
            ),
            created_at = "2023-08-08T12:16:02.427Z",
            updated_at = "2023-08-08T12:16:02.427Z",
            pointer = "",
            payment_method = "Pay.nl - Bancontact"
        )
    )
    Column(
        modifier = Modifier
            .verticalScroll(rememberScrollState())
            .padding(16.dp),

        ) {
        orders.forEach { order ->
            OverviewOrder(order = order)
        }
    }
}