package com.jsmecommerce.portal3scanner.activities

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxHeight
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.jsmecommerce.portal3scanner.models.Tab
import com.jsmecommerce.portal3scanner.stores.NavigationStore
import com.jsmecommerce.portal3scanner.ui.components.general.Title
import com.jsmecommerce.portal3scanner.ui.theme.Color
import com.jsmecommerce.portal3scanner.utils.Auth
import com.jsmecommerce.portal3scanner.utils.getRoutes
import com.jsmecommerce.portal3scanner.R
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : ComponentActivity() {
    var scanReceiver: BroadcastReceiver? = null

    class ScanBroadcastReceiver(val callback: ((barcode: String) -> Unit)? = null): BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            if(intent != null && intent.hasExtra("com.symbol.datawedge.data_string")) {
                val barcode: String = intent.getStringExtra("com.symbol.datawedge.data_string")!!
                callback?.let { it(barcode) }
            }
        }
    }

    @SuppressLint("StateFlowValueCalledInComposition", "UnspecifiedRegisterReceiverFlag")
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

        val tabs = listOf(
            Tab.DASHBOARD,
            Tab.ORDERS,
            Tab.SETTINGS
        )

        val routes = getRoutes()

        setContent {
            val navigationStore = NavigationStore()
            var navigation by remember { mutableStateOf(navigationStore.getState()) }
            var lastScan by remember { mutableStateOf("") }
            navigationStore.subscribe { navigation = navigationStore.getState() }
            val activeRoute = routes[navigation.tab]?.firstOrNull { it.path == navigation.currentPage }

            val scanIntent = IntentFilter("com.jsmecommerce.portal3scanner.SCAN")
            scanReceiver = ScanBroadcastReceiver { barcode ->
                Toast.makeText(applicationContext, barcode, Toast.LENGTH_SHORT).show()
            }
            registerReceiver(scanReceiver, scanIntent)

            Scaffold(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Background),
                topBar = {
                    TopAppBar(
                        colors = TopAppBarDefaults.mediumTopAppBarColors(
                            containerColor = Color.Menu
                        ),
                        title = { Title(activeRoute?.title ?: "") },
                        navigationIcon = {
                            if(navigation.history.isNotEmpty())
                                Surface(
                                    color = Color.Transparent,
                                    modifier = Modifier.fillMaxHeight(),
                                    onClick = { navigationStore.back() }
                                ) {
                                    Icon(
                                        painter = painterResource(id = R.drawable.ic_chevron_left),
                                        contentDescription = "Terug",
                                        tint = Color.TextSecondary,
                                        modifier = Modifier.padding(start = 16.dp, end = 16.dp)
                                    )
                                }
                        }
                    )
                },
                bottomBar = {
                    NavigationBar(
                        containerColor = Color.Menu
                    ) {
                        tabs.forEach { item ->
                            NavigationBarItem(
                                selected = navigation.tab == item.type,
                                onClick = { navigationStore.setTab(item.type) },
                                icon = { Icon(painter = painterResource(id = item.icon), contentDescription = item.title) },
                                colors = NavigationBarItemDefaults.colors(
                                    indicatorColor = Color.Primary.Regular,
                                    selectedIconColor = Color.White,
                                    selectedTextColor = Color.White,
                                    unselectedIconColor = Color.TextSecondary,
                                    unselectedTextColor = Color.TextSecondary
                                ),
                                label = { Text(item.title) }
                            )
                        }
                    }
                }
            ) { paddingValues ->
                Surface(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues),
                    color = Color.Background
                ) {
                    activeRoute?.render(this)
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        if(scanReceiver != null)
            unregisterReceiver(scanReceiver)
    }
}