package com.jsmecommerce.portal3scanner.activities

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import com.jsmecommerce.portal3scanner.R
import com.jsmecommerce.portal3scanner.activities.tabs.DashboardTab
import com.jsmecommerce.portal3scanner.activities.tabs.OrdersTab.OrdersOverview
import com.jsmecommerce.portal3scanner.activities.tabs.SettingsTab.SettingsOverview
import com.jsmecommerce.portal3scanner.ui.components.general.Jdenticon
import com.jsmecommerce.portal3scanner.ui.components.general.Title
import com.jsmecommerce.portal3scanner.ui.theme.Color
import com.jsmecommerce.portal3scanner.utils.Auth
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : ComponentActivity() {
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
            val navController = rememberNavController()
            val navBackStackEntry by navController.currentBackStackEntryAsState()
            val currentRoute = navBackStackEntry?.destination?.route ?: ""
            Scaffold(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Background),
                topBar = {
                    TopAppBar(
                        colors = TopAppBarDefaults.mediumTopAppBarColors(
                            containerColor = Color.Menu
                        ),
                        title = { Title(currentRoute ?: "test") }
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
                            selected = currentRoute == "dashboard",
                            onClick = { navController.navigate("dashboard") },
                            icon = { Icon(painter = painterResource(id = R.drawable.ic_home), contentDescription = "Home") },
                            colors = colors,
                            label = { Text("Dashboard") }
                        )
                        NavigationBarItem(
                            selected = currentRoute.startsWith("orders"),
                            onClick = { navController.navigate("orders") },
                            icon = { Icon(painter = painterResource(id = R.drawable.ic_orders), contentDescription = "Bestellingen") },
                            colors = colors,
                            label = { Text("Bestellingen") }
                        )
                        NavigationBarItem(
                            selected = currentRoute.startsWith("settings"),
                            onClick = { navController.navigate("settings") },
                            icon = { Icon(painter = painterResource(id = R.drawable.ic_settings), contentDescription = "Instellingen") },
                            colors = colors,
                            label = { Text("Instellingen") }
                        )
                        NavigationBarItem(
                            selected = currentRoute.startsWith("account"),
                            onClick = { navController.navigate("account") },
                            icon = {
                                Jdenticon("Test", size = 84)
                            },
                            colors = colors,
                            label = { Text("Account") }
                        )
                    }
                }
            ) { paddingValues ->
                Surface(
                    modifier = Modifier
                        .padding(paddingValues),
                    color = Color.Transparent
                ) {
                    NavHost(
                        navController = navController,
                        startDestination = "dashboard",
                        modifier = Modifier
                            .background(Color.Background)
                            .fillMaxSize()
                    ) {
                        composable("dashboard") { DashboardTab() }
                        composable("orders") { OrdersOverview() }
                        composable("settings") { SettingsOverview(onLogout = { logout() }) }
                        composable("settings/information") {  }
                    }
                }
            }
        }
    }
}