package com.jsmecommerce.portal3scanner.activities.tabs.SettingsTab

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.jsmecommerce.portal3scanner.viewmodels.MainViewModel
import com.jsmecommerce.portal3scanner.R
import com.jsmecommerce.portal3scanner.models.Scan
import com.jsmecommerce.portal3scanner.models.products.ScanProduct
import com.jsmecommerce.portal3scanner.ui.components.general.Description
import com.jsmecommerce.portal3scanner.ui.components.general.ScannerHost
import com.jsmecommerce.portal3scanner.ui.components.general.SimpleText
import com.jsmecommerce.portal3scanner.ui.components.general.Spinner
import com.jsmecommerce.portal3scanner.ui.components.screens.LoadingScreen
import com.jsmecommerce.portal3scanner.ui.theme.Color
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun SettingsScanner(nav: NavHostController, mvm: MainViewModel) {
    var portalProductLoading by remember { mutableStateOf(false) }
    var portalProduct by remember { mutableStateOf<ScanProduct?>(null) }
    var scan by remember { mutableStateOf<Scan?>(null) }
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        mvm.init(
            title = context.getString(R.string.settings_scanner_title)
        )
    }

    ScannerHost(nav = nav) {
        portalProduct = null
        scan = it
        if(it.barcodeType == Scan.BarcodeType.EAN)
            CoroutineScope(Dispatchers.IO).launch {
                portalProductLoading = true
                portalProduct = ScanProduct.findByBarcode(context, it.barcode)
                portalProductLoading = false
            }
    }

    if(scan == null)
        LoadingScreen(text = R.string.settings_scanner_waiting)
    else
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .padding(16.dp),

        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.Element)
                    .clip(RoundedCornerShape(4))
                    .padding(8.dp)
            ) {
                Row {
                    SimpleText("Label type", modifier = Modifier.width(164.dp))
                    Description(scan!!.labelType.toString())
                }
                Spacer(modifier = Modifier.height(6.dp))
                Row {
                    SimpleText("Code type", modifier = Modifier.width(164.dp))
                    Description(scan!!.barcodeType?.toString() ?: "Unknown")
                }
                Spacer(modifier = Modifier.height(6.dp))
                Row {
                    SimpleText("Code sub-type", modifier = Modifier.width(164.dp))
                    Description(scan!!.barcodeSubType?.toString() ?: "Unknown")
                }
                Spacer(modifier = Modifier.height(6.dp))
                Row {
                    SimpleText("Barcode", modifier = Modifier.width(164.dp))
                    Description(scan!!.barcode)
                }
                if(scan!!.barcodeType == Scan.BarcodeType.SHIPMENT) {
                    Spacer(modifier = Modifier.height(6.dp))
                    Row {
                        SimpleText("Shipment barcode", modifier = Modifier.width(164.dp))
                        Description(scan!!.getShipmentBarcode())
                    }
                }
                if(portalProductLoading) {
                    Spacer(modifier = Modifier.height(6.dp))
                    Row {
                        SimpleText("Portal product", modifier = Modifier.width(164.dp))
                        Spinner()
                    }
                }
                if(portalProduct != null) {
                    Spacer(modifier = Modifier.height(6.dp))
                    Row {
                        SimpleText("Portal product", modifier = Modifier.width(164.dp))
                        Description(portalProduct!!.name)
                    }
                }
            }
        }
}