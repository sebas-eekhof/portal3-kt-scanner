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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
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
import com.jsmecommerce.portal3scanner.BuildConfig
import com.jsmecommerce.portal3scanner.R
import com.jsmecommerce.portal3scanner.activities.tabs.DashboardTab
import com.jsmecommerce.portal3scanner.activities.tabs.OrdersTab.OrderView
import com.jsmecommerce.portal3scanner.activities.tabs.OrdersTab.OrdersOverview
import com.jsmecommerce.portal3scanner.activities.tabs.SettingsTab.SettingsInformation
import com.jsmecommerce.portal3scanner.activities.tabs.SettingsTab.SettingsLanguage
import com.jsmecommerce.portal3scanner.activities.tabs.SettingsTab.SettingsOverview
import com.jsmecommerce.portal3scanner.activities.tabs.SettingsTab.SettingsScanner
import com.jsmecommerce.portal3scanner.models.Popup
import com.jsmecommerce.portal3scanner.models.Tab
import com.jsmecommerce.portal3scanner.models.UpdateVersion
import com.jsmecommerce.portal3scanner.ui.components.general.Spinner
import com.jsmecommerce.portal3scanner.ui.components.general.Title
import com.jsmecommerce.portal3scanner.ui.components.general.TopBar
import com.jsmecommerce.portal3scanner.ui.components.screens.UpdateScreen
import com.jsmecommerce.portal3scanner.ui.theme.Color
import com.jsmecommerce.portal3scanner.utils.Api
import com.jsmecommerce.portal3scanner.viewmodels.CoreViewModel
import com.jsmecommerce.portal3scanner.viewmodels.OrdersOverviewViewModel
import com.jsmecommerce.portal3scanner.viewmodels.UiViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

val tabs = listOf(
    Tab.DASHBOARD,
    Tab.ORDERS,
    Tab.SETTINGS
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainView(
    uiViewModel: UiViewModel,
    coreViewModel: CoreViewModel
) {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    val title: String by uiViewModel.title.observeAsState("NO TITLE")
    val backEnabled: Boolean by uiViewModel.backEnabled.observeAsState(false)
    val loading: Boolean by uiViewModel.loading.observeAsState(false)
    val popup: Popup? by uiViewModel.popup.observeAsState(null)
    val actions: (@Composable () -> Unit)? by uiViewModel.actions.observeAsState(null)
    var updateVersion by remember { mutableStateOf<UpdateVersion?>(null) }

    val context = LocalContext.current;

    LaunchedEffect(Unit) {
//        coreViewModel.scans.onEach {
//            println("TESTTEST SCAN: ${it.barcode}")
//        }
        CoroutineScope(Dispatchers.IO).launch {
            val res = Api.Request(context, "/scanner_versions/latest")
                .exec()
            if(!res.hasError) {
                val version = UpdateVersion.fromJSON(res.getJsonObject()!!)
                if(BuildConfig.VERSION_CODE < version.version_code)
                    updateVersion = version
            }
        }
    }

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
            TopBar(
                uiViewModel = uiViewModel,
                coreViewModel = coreViewModel
            )
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
                                actions?.let { it() }
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
                    NavHost(
                        navController = navController,
                        startDestination = "dashboard"
                    ) {
                        composable("dashboard") { DashboardTab(navController, coreViewModel, uiViewModel) }
                        navigation(startDestination = "orders/overview", route = "orders") {
                            composable("orders/overview") {
                                OrdersOverview(
                                    navController,
                                    coreViewModel,
                                    uiViewModel,
                                    OrdersOverviewViewModel(LocalContext.current)
                                )
                            }
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
                                    OrderView(navController, coreViewModel, uiViewModel, orderId, orderTitle)
                            }
                        }
                        navigation(startDestination = "settings/overview", route = "settings") {
                            composable("settings/overview") { SettingsOverview(navController, coreViewModel, uiViewModel) }
                            composable("settings/information") { SettingsInformation(navController, coreViewModel, uiViewModel) }
                            composable("settings/scanner") { SettingsScanner(navController, coreViewModel, uiViewModel) }
                            composable("settings/language") { SettingsLanguage(navController, coreViewModel, uiViewModel) }
                        }
                    }
                }
            }
        }
        Box(modifier = Modifier.fillMaxSize()) {
            if(popup != null) {
                if(popup!!.raw)
                    popup!!.content()
                else
                    popup!!.Render {
                        uiViewModel.setPopup(null)
                    }
            }
            if(updateVersion != null)
                UpdateScreen(version = updateVersion!!)
        }
    }
}