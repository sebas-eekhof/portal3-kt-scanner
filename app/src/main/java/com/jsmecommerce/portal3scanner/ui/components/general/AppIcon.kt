package com.jsmecommerce.portal3scanner.ui.components.general

import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.bumptech.glide.load.engine.DiskCacheStrategy

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun AppIcon(identifier: String, icon: String = "icon.webp", size: Dp = 48.dp) {
    GlideImage(
        model = "https://cdn.portal3.nl/_app/$identifier/$icon",
        contentDescription = identifier,
        contentScale = ContentScale.Crop,
        modifier = Modifier
            .size(size)
            .clip(RoundedCornerShape(7))
    ) {
        it.diskCacheStrategy(DiskCacheStrategy.ALL)
    }
}