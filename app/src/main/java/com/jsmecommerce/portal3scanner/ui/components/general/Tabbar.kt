package com.jsmecommerce.portal3scanner.ui.components.general

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.jsmecommerce.portal3scanner.models.TabbarTab
import com.jsmecommerce.portal3scanner.ui.theme.Color

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Tabbar(tabs: List<TabbarTab>, defaultTab: Int = 0) {
    var activeTab by remember { mutableStateOf(defaultTab) }

    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.Danger.Regular)
        ) {
            for(i in 0 until tabs.count()) {
                val tab = tabs[i]
                Surface(
                    color = if (activeTab == i) Color.Primary.Regular else Color.Element,
                    modifier = Modifier
                        .weight(1f)
                        .height(48.dp),
                    onClick = { activeTab = i }
                ) {
                    Column(
                        modifier = Modifier
                            .height(48.dp)
                            .fillMaxWidth(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        SimpleText(stringResource(tab.name))
                    }
                }
            }
        }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                tabs[activeTab].component()
            }
        }
    }
}