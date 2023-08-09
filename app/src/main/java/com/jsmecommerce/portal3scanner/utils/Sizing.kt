package com.jsmecommerce.portal3scanner.utils

import android.content.res.Resources
import android.util.DisplayMetrics
import android.util.TypedValue
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

val Number.toDp get() = (this.toFloat() / (Resources.getSystem().displayMetrics.densityDpi.toFloat() / DisplayMetrics.DENSITY_DEFAULT)).dp
val Number.toPx get() = (this.toFloat() * (Resources.getSystem().displayMetrics.densityDpi.toFloat() / DisplayMetrics.DENSITY_DEFAULT)).toInt()
val Dp.toPx get() = (this.value * (Resources.getSystem().displayMetrics.densityDpi.toFloat() / DisplayMetrics.DENSITY_DEFAULT)).toInt()