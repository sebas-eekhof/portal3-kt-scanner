package com.jsmecommerce.portal3scanner.ui.components.popups

import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.jsmecommerce.portal3scanner.R
import com.jsmecommerce.portal3scanner.models.SelectItem
import com.jsmecommerce.portal3scanner.ui.components.form.Select
import com.jsmecommerce.portal3scanner.ui.components.general.SmallTitle
import com.jsmecommerce.portal3scanner.ui.components.general.Title
import com.jsmecommerce.portal3scanner.ui.theme.Color

@Composable
fun FilterPopup(
    @StringRes title: Int = R.string.filters
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Spacer(modifier = Modifier.height(64.dp))
        Column(
            modifier = Modifier
                .background(Color.Background)
                .fillMaxWidth()
                .weight(1f)
        ) {
            Column(
                modifier = Modifier
                    .padding(16.dp)
            ) {
                Title(stringResource(id = title))
                Spacer(modifier = Modifier.height(16.dp))
                Select(label = R.string.filters, items = listOf(
                    SelectItem("Test item1", "1"),
                    SelectItem("Test item2", "1"),
                    SelectItem("Test item3", "1"),
                    SelectItem("Test item4", "1"),
                    SelectItem("Test item5", "1"),
                    SelectItem("Test item6", "1")
                ))
            }
        }
    }
}