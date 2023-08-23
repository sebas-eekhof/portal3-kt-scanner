package com.jsmecommerce.portal3scanner.ui.components.general

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import com.jsmecommerce.portal3scanner.viewmodels.UiViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun Clock(uiViewModel: UiViewModel) {
    val date: Date by uiViewModel.date.observeAsState(Date())
    SimpleText(SimpleDateFormat("dd MMM   HH:mm", Locale("NL", "NL")).format(date))
}