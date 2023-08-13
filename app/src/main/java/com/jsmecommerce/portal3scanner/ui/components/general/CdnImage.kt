package com.jsmecommerce.portal3scanner.ui.components.general

import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.jsmecommerce.portal3scanner.utils.Auth
import com.jsmecommerce.portal3scanner.utils.toPx
import java.net.URL

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun CdnImage(path: String, size: Dp = 48.dp, quality: Int = 75) {
    val context = LocalContext.current
    val auth = Auth(context)
    val jwt = auth.getJWT()
    GlideImage(
        model = "https://cdn.portal3.nl${if (path.startsWith("/")) path else "/$path"}?height=${size.toPx}&width=${size.toPx}&quality=$quality${if (jwt == null) "" else "&session_id=$jwt"}",
        contentDescription = "",
        contentScale = ContentScale.Crop,
        modifier = androidx.compose.ui.Modifier
            .size(size)
            .clip(RoundedCornerShape(7))
    ) {
        it.diskCacheStrategy(DiskCacheStrategy.ALL)
    }
}