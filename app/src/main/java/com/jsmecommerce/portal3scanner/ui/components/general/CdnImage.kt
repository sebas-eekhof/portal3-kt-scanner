package com.jsmecommerce.portal3scanner.ui.components.general

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.jsmecommerce.portal3scanner.ui.theme.Color
import com.jsmecommerce.portal3scanner.utils.Auth
import com.jsmecommerce.portal3scanner.utils.toDp
import com.jsmecommerce.portal3scanner.utils.toPx

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun CdnImage(path: String, size: Dp = 48.dp, quality: Int = 75) {
    val context = LocalContext.current
    val auth = Auth(context)
    val jwt = auth.getJWT()
    Surface(
        modifier = Modifier
            .size(size),
        color = Color.White,
        shape = RoundedCornerShape(7)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            GlideImage(
                model = "https://cdn.portal3.nl${if (path.startsWith("/")) path else "/$path"}?height=${size.toPx}&width=${size.toPx}&quality=$quality${if (jwt == null) "" else "&session_id=$jwt"}",
                contentDescription = "",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(size - (4.toDp))
                    .clip(RoundedCornerShape(7))
            ) {
                it.diskCacheStrategy(DiskCacheStrategy.ALL)
            }
        }
    }
}