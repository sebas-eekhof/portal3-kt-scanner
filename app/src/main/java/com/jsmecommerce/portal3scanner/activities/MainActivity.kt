package com.jsmecommerce.portal3scanner.activities

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import com.jsmecommerce.portal3scanner.R
import com.jsmecommerce.portal3scanner.activities.tabs.DashboardTab
import com.jsmecommerce.portal3scanner.activities.tabs.OrdersTab
import com.jsmecommerce.portal3scanner.activities.tabs.SettingsTab
import com.jsmecommerce.portal3scanner.ui.components.general.Jdenticon
import com.jsmecommerce.portal3scanner.ui.components.general.Title
import com.jsmecommerce.portal3scanner.ui.components.general.TopAppBarAction
import com.jsmecommerce.portal3scanner.ui.theme.Color
import com.jsmecommerce.portal3scanner.ui.theme.Portal3ScannerTheme
import com.jsmecommerce.portal3scanner.utils.Auth
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : ComponentActivity() {
    enum class Tab(val title: String, val actions: (@Composable () -> Unit)? = null) {
        DASHBOARD(
            title = "Dashboard"
        ),
        ORDERS(
            title = "Bestellingen",
            actions = {
                TopAppBarAction(
                    icon = R.drawable.ic_filter,
                    onClick = {

                    }
                )
            }
        ),
        SETTINGS(
            title = "Instellingen"
        ),
        ACCOUNT(
            title = "Account"
        )
    }

    @OptIn(DelicateCoroutinesApi::class, ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val auth = Auth(applicationContext)

        val user = auth.getUser()

        if(user == null) {
            startActivity(Intent(this, AuthActivity::class.java))
            finish()
            return
        }

        fun logout() {
            val builder = AlertDialog.Builder(this)
            builder.setMessage("Weet u zeker dat u wilt uitloggen?")
                .setCancelable(true)
                .setPositiveButton("Ja") { _, _ ->
                    GlobalScope.launch(Dispatchers.IO) {
                        auth.logout()
                        withContext(Dispatchers.Main) {
                            startActivity(Intent(
                                applicationContext,
                                AuthActivity::class.java
                            ))
                            finish()
                        }
                    }
                }
                .setNegativeButton("Nee") { dialog, _ ->
                    dialog.dismiss()
                }
            val alert = builder.create()
            alert.show()
        }

        setContent {
            var tab by remember { mutableStateOf(Tab.DASHBOARD) }

            Portal3ScannerTheme {
                Scaffold(
                    topBar = {
                        TopAppBar(
                            colors = TopAppBarDefaults.mediumTopAppBarColors(
                                containerColor = Color.Menu
                            ),
                            title = {
                                Title(tab.title)
                            },
                            navigationIcon = {
//                                Icon(painter = painterResource(id = R.drawable.ic_arrow_left), contentDescription = "Back")
                            },
                            actions = {
                                tab.actions?.let { it() }
                            }
                        )
                    },
                    bottomBar = {
                        NavigationBar(
                            containerColor = Color.Menu
                        ) {
                            val colors = NavigationBarItemDefaults.colors(
                                indicatorColor = Color.Primary.Regular,
                                selectedIconColor = Color.White,
                                selectedTextColor = Color.White,
                                unselectedIconColor = Color.TextSecondary,
                                unselectedTextColor = Color.TextSecondary
                            )
                            NavigationBarItem(
                                selected = (tab == Tab.DASHBOARD),
                                onClick = { tab = Tab.DASHBOARD },
                                icon = { Icon(painter = painterResource(id = R.drawable.ic_home), contentDescription = "Home") },
                                colors = colors,
                                label = { Text("Dashboard") }
                            )
                            NavigationBarItem(
                                selected = (tab == Tab.ORDERS),
                                onClick = { tab = Tab.ORDERS },
                                icon = { Icon(painter = painterResource(id = R.drawable.ic_orders), contentDescription = "Bestellingen") },
                                colors = colors,
                                label = { Text("Bestellingen") }
                            )
                            NavigationBarItem(
                                selected = (tab == Tab.SETTINGS),
                                onClick = { tab = Tab.SETTINGS },
                                icon = { Icon(painter = painterResource(id = R.drawable.ic_settings), contentDescription = "Instellingen") },
                                colors = colors,
                                label = { Text("Instellingen") }
                            )
                            NavigationBarItem(
                                selected = (tab == Tab.ACCOUNT),
                                onClick = { tab = Tab.ACCOUNT },
                                icon = {
                                    Jdenticon("Test", size = 84)
                                },
                                colors = colors,
                                label = { Text("Account") }
                            )
                        }
                    },
                    modifier = Modifier
                        .fillMaxSize()
                ) { paddingValues ->
                    Surface(
                        modifier = Modifier
                            .padding(paddingValues)
                            .fillMaxSize()
                    ) {
                        Column {
                            if(tab == Tab.DASHBOARD)
                                DashboardTab()
                            if(tab == Tab.ORDERS)
                                OrdersTab()
                            if(tab == Tab.SETTINGS)
                                SettingsTab(
                                    onLogout = { logout() }
                                )
                        }
                    }
                }
            }
        }
    }
}