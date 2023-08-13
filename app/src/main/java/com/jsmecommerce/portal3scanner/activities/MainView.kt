package com.jsmecommerce.portal3scanner.activities

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavDeepLink
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.navigation.navDeepLink
import com.jsmecommerce.portal3scanner.R
import com.jsmecommerce.portal3scanner.activities.tabs.DashboardTab
import com.jsmecommerce.portal3scanner.activities.tabs.OrdersTab.OrderView
import com.jsmecommerce.portal3scanner.activities.tabs.OrdersTab.OrdersOverview
import com.jsmecommerce.portal3scanner.activities.tabs.SettingsTab.SettingsInformation
import com.jsmecommerce.portal3scanner.activities.tabs.SettingsTab.SettingsLanguage
import com.jsmecommerce.portal3scanner.activities.tabs.SettingsTab.SettingsOverview
import com.jsmecommerce.portal3scanner.activities.tabs.SettingsTab.SettingsScanner
import com.jsmecommerce.portal3scanner.models.Tab
import com.jsmecommerce.portal3scanner.ui.components.general.Spinner
import com.jsmecommerce.portal3scanner.ui.components.general.Title
import com.jsmecommerce.portal3scanner.ui.components.general.TopBar
import com.jsmecommerce.portal3scanner.ui.components.screens.TutorialScreen
import com.jsmecommerce.portal3scanner.ui.theme.Color
import com.jsmecommerce.portal3scanner.viewmodels.MainViewModel

val tabs = listOf(
    Tab.DASHBOARD,
    Tab.ORDERS,
    Tab.SETTINGS
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainView(mvm: MainViewModel = viewModel()) {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    val title: String by mvm.title.observeAsState("NO TITLE")
    val backEnabled: Boolean by mvm.backEnabled.observeAsState(false)
    val loading: Boolean by mvm.loading.observeAsState(false)
    val popup: (@Composable () -> Unit)? by mvm.popup.observeAsState(null)

    fun Portal3DeepLinks(path: String): List<NavDeepLink> = listOf(
        NavDeepLink(
            uri = "https://ui.portal3.nl${if (path.startsWith("/")) path else "/$path"}"
        ),
        NavDeepLink(
            uri = "app://portal3${if (path.startsWith("/")) path else "/$path"}"
        )
    )

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            TopBar(mvm = mvm)
            Scaffold(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Background),
                topBar = {
                    TopAppBar(
                        colors = TopAppBarDefaults.mediumTopAppBarColors(
                            containerColor = Color.Menu
                        ),
                        title = { Title(title) },
                        actions = {
                            Row(
                                modifier = Modifier.padding(end = 8.dp),
                                horizontalArrangement = Arrangement.End,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                if(loading)
                                    Spinner(color = Color.TextSecondary)
                            }
                        },
                        navigationIcon = {
                            if(backEnabled)
                                Surface(
                                    color = Color.Transparent,
                                    modifier = Modifier.fillMaxHeight(),
                                    onClick = { navController.popBackStack() }
                                ) {
                                    Icon(
                                        painter = painterResource(id = R.drawable.ic_chevron_left),
                                        contentDescription = "Terug",
                                        tint = Color.TextSecondary,
                                        modifier = Modifier.padding(start = 4.dp, end = 16.dp)
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
                                selected = currentDestination?.hierarchy?.any { it.route == item.route } == true,
                                onClick = {
                                    navController.navigate(item.route) {
                                        popUpTo(navController.graph.findStartDestination().id) {
                                            saveState = true
                                        }
                                        launchSingleTop = true
                                        restoreState = true
                                    }
                                },
                                icon = { Icon(painter = painterResource(id = item.icon), contentDescription = stringResource(id = item.title)) },
                                colors = NavigationBarItemDefaults.colors(
                                    indicatorColor = Color.Primary.Regular,
                                    selectedIconColor = Color.White,
                                    selectedTextColor = Color.White,
                                    unselectedIconColor = Color.TextSecondary,
                                    unselectedTextColor = Color.TextSecondary
                                ),
                                label = { Text(stringResource(id = item.title)) }
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
                    NavHost(navController = navController, startDestination = "dashboard") {
                        composable("dashboard") { DashboardTab(navController, mvm) }
                        navigation(startDestination = "orders/overview", route = "orders") {
                            composable("orders/overview") { OrdersOverview(navController, mvm) }
                            composable(
                                "orders/view/{orderId}?title={title}",
                                deepLinks = Portal3DeepLinks("/orders/{orderId}"),
                                arguments = listOf(
                                    navArgument("orderId") { type = NavType.IntType },
                                    navArgument("title") {
                                        type = NavType.StringType
                                        defaultValue = "Order"
                                    }
                                )
                            ) {
                                val orderId: Int? = it.arguments?.getInt("orderId")
                                val orderTitle: String? = it.arguments?.getString("title")
                                if(orderId != null && orderTitle != null)
                                    OrderView(navController, mvm, orderId, orderTitle)
                            }
                        }
                        navigation(startDestination = "settings/overview", route = "settings") {
                            composable("settings/overview") { SettingsOverview(navController, mvm) }
                            composable("settings/information") { SettingsInformation(navController, mvm) }
                            composable("settings/scanner") { SettingsScanner(navController, mvm) }
                            composable("settings/language") { SettingsLanguage(navController, mvm) }
                        }
                    }
                }
            }
        }
        popup?.invoke()
    }
}