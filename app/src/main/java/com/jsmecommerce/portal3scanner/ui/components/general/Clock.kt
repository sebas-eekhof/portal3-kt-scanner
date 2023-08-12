package com.jsmecommerce.portal3scanner.ui.components.general

import android.os.Handler
import android.widget.TextClock
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.viewinterop.AndroidView
import com.jsmecommerce.portal3scanner.viewmodels.MainViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import kotlin.concurrent.fixedRateTimer

@Composable
fun Clock(mvm: MainViewModel) {
    val time: String by mvm.time.observeAsState("")

    DisposableEffect(LocalLifecycleOwner.current) {
        val handler = Handler()
        val timer = fixedRateTimer("timer", false, 0L, 60 * 1000) {
            handler.post {
                mvm._time.value = SimpleDateFormat("dd MMM   HH:mm", Locale("NL", "NL")).format(Date())
            }
        }
        onDispose {
            timer.cancel()
        }
    }

    SimpleText(time)
}