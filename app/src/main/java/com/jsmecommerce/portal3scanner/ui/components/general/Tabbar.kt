package com.jsmecommerce.portal3scanner.ui.components.general

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.jsmecommerce.portal3scanner.models.TabbarTab
import com.jsmecommerce.portal3scanner.ui.theme.Color

@Composable
fun Tabbar(tabs: List<TabbarTab>) {
    LazyVerticalGrid(
        columns = GridCells.Adaptive(minSize = 64.dp),
        content = {
            items(items = tabs) { tab ->
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Surface(
                        color = Color.Element,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        SimpleText(stringResource(id = tab.name))
                    }
                }
            }
        }
    )
}