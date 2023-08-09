package com.jsmecommerce.portal3scanner.ui.components.general

import android.content.res.Resources
import android.graphics.drawable.PictureDrawable
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.caverock.androidsvg.SVG
import com.jsmecommerce.portal3scanner.ui.theme.Color
import com.jsmecommerce.portal3scanner.utils.toDp
import com.jsmecommerce.portal3scanner.utils.toPx
import jdenticon.Jdenticon

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun Jdenticon(jdenticon: String, size: Int = 128, boxed: Boolean = false) {
    val model = PictureDrawable(SVG.getFromString(Jdenticon.toSvg(hashOrValue = jdenticon, size = (size.toDp - 8.dp).toPx)).renderToPicture())
    if(boxed)
        Column(
            modifier = Modifier
                .background(Color.Subelement, RoundedCornerShape(4))
                .clip(RoundedCornerShape(4))
                .size(size.toDp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            GlideImage(
                model = model,
                contentDescription = "Jdenticon"
            )
        }
    else
        GlideImage(
            model = model,
            contentDescription = "Jdenticon"
        )
}