package com.jsmecommerce.portal3scanner.ui.components.general

import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.navigation.NavHostController
import com.jsmecommerce.portal3scanner.datasource.portal3api.Portal3Api
import com.jsmecommerce.portal3scanner.models.Scan
import com.jsmecommerce.portal3scanner.utils.Device
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ScannerReceiver(val callback: (scan: Scan) -> Unit) : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        if(
            intent != null &&
            intent.hasExtra("com.symbol.datawedge.label_type") &&
            intent.hasExtra("com.symbol.datawedge.scanner_identifier") &&
            intent.hasExtra("com.symbol.datawedge.data_string")
        ) {
            val data = intent.getStringExtra("com.symbol.datawedge.data_string")
            val labelTypeString = intent.getStringExtra("com.symbol.datawedge.label_type")
            if(data != null && labelTypeString != null) {
                val labelType = when(labelTypeString) {
                    "LABEL-TYPE-EAN13" -> Scan.LabelType.EAN13
                    "LABEL-TYPE-QRCODE" -> Scan.LabelType.QRCODE
                    "LABEL-TYPE-EAN8" -> Scan.LabelType.EAN8
                    "LABEL-TYPE-PDF417" -> Scan.LabelType.PDF417
                    "LABEL-TYPE-GS1-DATABAR" -> Scan.LabelType.GS1_DATABAR
                    "LABEL-TYPE-EAN128" -> Scan.LabelType.EAN128
                    "LABEL-TYPE-CODE128" -> Scan.LabelType.CODE128
                    "LABEL-TYPE-CODE39" -> Scan.LabelType.CODE39
                    "LABEL-TYPE-UPCA" -> Scan.LabelType.UPCA
                    "LABEL-TYPE-UPCE0" -> Scan.LabelType.UPCE0
                    "LABEL-TYPE-DATAMATRIX" -> Scan.LabelType.DATAMATRIX
                    "LABEL-TYPE-AZTEC" -> Scan.LabelType.AZTEC
                    "LABEL-TYPE-MAXICODE" -> Scan.LabelType.DATAMATRIX
                    else -> Scan.LabelType.UNDEFINED
                }

                if(labelType == Scan.LabelType.UNDEFINED) {
                    println("Undefined label type ${labelTypeString} - ${data}")
                }

                callback(
                    Scan(
                        barcodeRaw = data,
                        labelType = labelType
                    )
                )
            }
//            val scanner = intent.getStringExtra("com.symbol.datawedge.scanner_identifier")
        }
    }

}

@SuppressLint("UnspecifiedRegisterReceiverFlag")
@Composable
fun ScannerHost(nav: NavHostController, onScan: ((scan: Scan) -> Unit)? = null) {
    val context = LocalContext.current
    val api = Portal3Api.getInstance(context)

    DisposableEffect(LocalLifecycleOwner.current) {
        val receiver = ScannerReceiver { scan ->
            Device(context).vibrateScan()
            println("[SCAN]\nlabelType = ${scan.labelType}\nbarcodeType = ${scan.barcodeType ?: "NULL"}\nbarcodeSubType = ${scan.barcodeSubType ?: "NULL"}\nbarcode = ${scan.barcode}\n[/SCAN]")
            if(onScan != null)
                onScan(scan)
            else {
                if(scan.barcodeType == Scan.BarcodeType.SHIPMENT) {
                    CoroutineScope(Dispatchers.IO).launch {
                        val res = api.shipments.byBarcode(scan.getShipmentBarcode())
                        if(res.isSuccessful) {
                            val label = res.body()
                            if(label != null)
                                withContext(Dispatchers.Main) {
                                    nav.navigate("orders/view/${label.order.id}?title=${label.order.orderNumberFull}")
                                }
                        } else
                            withContext(Dispatchers.Main) {
                                Toast.makeText(context, "Unknown shipment label", Toast.LENGTH_LONG).show()
                            }
                    }
                } else {
//                    Toast.makeText(context, scan.barcode, Toast.LENGTH_SHORT).show()
                }
            }
        }
        context.registerReceiver(receiver, IntentFilter("com.jsmecommerce.portal3scanner.SCAN"))
        onDispose {
            context.unregisterReceiver(receiver)
        }
    }
}