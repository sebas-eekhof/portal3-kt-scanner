package com.jsmecommerce.portal3scanner.ui.components.screens

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.jsmecommerce.portal3scanner.ui.components.general.Description
import com.jsmecommerce.portal3scanner.R
import com.jsmecommerce.portal3scanner.ui.components.general.Spinner
import com.jsmecommerce.portal3scanner.ui.theme.Color

@Composable
fun LoadingScreen(@StringRes text: Int = R.string.please_wait_fetching_data) {
    Row(
        modifier = Modifier.fillMaxSize(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Spinner(color = Color.TextSecondary)
        Spacer(modifier = Modifier.width(16.dp))
        Description(text = stringResource(id = text))
    }
}