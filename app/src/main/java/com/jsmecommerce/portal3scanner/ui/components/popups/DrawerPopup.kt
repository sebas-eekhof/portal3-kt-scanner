package com.jsmecommerce.portal3scanner.ui.components.popups

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.animateIntOffsetAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.absoluteOffset
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.jsmecommerce.portal3scanner.ui.components.general.SimpleText
import com.jsmecommerce.portal3scanner.ui.components.general.Title
import com.jsmecommerce.portal3scanner.ui.theme.Color
import com.jsmecommerce.portal3scanner.utils.Device
import com.jsmecommerce.portal3scanner.utils.toDp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DrawerPopup(title: String, onClose: () -> Unit, content: @Composable () -> Unit) {
    var visible by remember { mutableStateOf(false) }
    val backdropAlpha by animateFloatAsState(
        targetValue = if (visible) 0.75f else 0f,
        label = "Drawer Backdrop Animation",
        animationSpec = tween(
            durationMillis = 300,
            easing = LinearEasing
        )
    )
    val drawerOffset by animateDpAsState(
        targetValue = if (visible) 0.dp else Device(LocalContext.current).screenHeight,
        label = "Drawer Offset Animation",
        animationSpec = tween(
            durationMillis = 300,
            easing = LinearEasing
        )
    )

    LaunchedEffect(Unit) {
        visible = true
    }

    fun doClose() {
        visible = false
        CoroutineScope(Dispatchers.Unconfined).launch {
            delay(500)
            withContext(Dispatchers.Main) {
                onClose()
            }
        }
    }

    Box {
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .alpha(backdropAlpha),
            color = Color.Black,
            onClick = { doClose() }
        ) {}
        Column(
            modifier = Modifier
                .fillMaxSize()
                .offset(y = drawerOffset),
            verticalArrangement = Arrangement.Bottom
        ) {
            Column(
                modifier = Modifier
                    .background(
                        Color.Background,
                        RoundedCornerShape(topStartPercent = 7, topEndPercent = 7)
                    )
            ) {
                Column(
                    modifier = Modifier
                        .padding(16.dp)
                ) {
                    Title(text = title)
                    Spacer(modifier = Modifier.height(16.dp))
                    content()
                }
            }
        }
    }
}