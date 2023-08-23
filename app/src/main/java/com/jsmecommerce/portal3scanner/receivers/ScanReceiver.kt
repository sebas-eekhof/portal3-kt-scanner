package com.jsmecommerce.portal3scanner.receivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.jsmecommerce.portal3scanner.models.Scan
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class ScanReceiver(private val callback: suspend (scan: Scan) -> Unit) : BroadcastReceiver() {
    @OptIn(DelicateCoroutinesApi::class)
    override fun onReceive(context: Context?, intent: Intent?) {
        if(context == null || intent == null) return
        GlobalScope.launch {
            callback(Scan.fromIntent(intent) ?: return@launch)
        }
    }
}