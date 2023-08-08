package com.jsmecommerce.portal3scanner.activities

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.KeyEvent
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
import com.symbol.emdk.EMDKBase
import com.symbol.emdk.EMDKManager
import com.symbol.emdk.EMDKManager.EMDKListener
import com.symbol.emdk.EMDKManager.StatusListener
import com.symbol.emdk.ProfileManager
import com.symbol.emdk.ProfileManager.DataListener
import com.symbol.emdk.barcode.BarcodeManager
import com.symbol.emdk.barcode.Scanner
import com.symbol.emdk.barcode.ScannerException
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : ComponentActivity(), EMDKListener {
    var emdkManager: EMDKManager? = null
    var scanner: Scanner? = null

    @SuppressLint("StateFlowValueCalledInComposition")
    @OptIn(DelicateCoroutinesApi::class, ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val test = EMDKManager.getEMDKManager(applicationContext, this)

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
            navigationStore.subscribe { navigation = navigationStore.getState() }
            val activeRoute = routes[navigation.tab]?.firstOrNull { it.path == navigation.currentPage }

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
                    activeRoute?.render()
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        releaseEMDK()
    }

    fun releaseEMDK() {
        emdkManager?.release()
        emdkManager = null
    }

    override fun onOpened(manager: EMDKManager?) {
        emdkManager = manager
        val barcodeManager = emdkManager?.getInstance(EMDKManager.FEATURE_TYPE.BARCODE) as BarcodeManager?
        if(barcodeManager == null) {
            println("EMDK Barcode scanning not supported")
            releaseEMDK()
            return
        }
        val scanner = barcodeManager.getDevice(BarcodeManager.DeviceIdentifier.INTERNAL_IMAGER1)
        if(scanner == null) {
            println("EMDK Default scanner not found")
            releaseEMDK()
            return
        }
        scanner.addDataListener {
            println("EMDK Scanner data: ${it}")
        }
        scanner.addStatusListener {
            println("EMDK Scanner status: ${it.state.name}")
        }
        scanner.triggerType = Scanner.TriggerType.HARD

        try {
            scanner.enable()
            this.scanner = scanner
        } catch(e: ScannerException) {
            e.printStackTrace()
            releaseEMDK()
        }
    }

    override fun onClosed() {
        releaseEMDK()
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if(scanner !== null && !scanner!!.isReadPending) {
            if(keyCode == 10036 || keyCode == 103) {
                try {
                    scanner!!.read()
                } catch(e: ScannerException) {
                    e.printStackTrace()
                }
                println("Setting scanner to read")
                return true
            }
        }
        return super.onKeyDown(keyCode, event)
    }

    override fun onKeyUp(keyCode: Int, event: KeyEvent?): Boolean {
        if(scanner !== null && !scanner!!.isReadPending) {
            if(keyCode == 10036 || keyCode == 103) {
                try {
                    scanner!!.cancelRead()
                } catch(e: ScannerException) {
                    e.printStackTrace()
                }
                println("Stopping scanner to read")
                return true
            }
        }
        return super.onKeyUp(keyCode, event)
    }
}