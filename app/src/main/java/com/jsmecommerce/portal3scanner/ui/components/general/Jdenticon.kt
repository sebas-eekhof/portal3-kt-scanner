package com.jsmecommerce.portal3scanner.ui.components.general

import android.graphics.drawable.PictureDrawable
import androidx.compose.runtime.Composable
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.caverock.androidsvg.SVG
import jdenticon.Jdenticon

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun Jdenticon(jdenticon: String, size: Int = 128) {
    val model = PictureDrawable(SVG.getFromString(Jdenticon.toSvg(hashOrValue = jdenticon, size = size)).renderToPicture())
    GlideImage(
        model = model,
        contentDescription = "Jdenticon"
    )
}