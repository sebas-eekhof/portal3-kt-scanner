package com.jsmecommerce.portal3scanner.ui.components.general

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import com.jsmecommerce.portal3scanner.ui.theme.Color
import com.jsmecommerce.portal3scanner.ui.theme.Portal3ScannerTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Page(title: String) {
    Portal3ScannerTheme {
        Scaffold(
            topBar = {
                TopAppBar(
                    colors = TopAppBarDefaults.mediumTopAppBarColors(
                        containerColor = Color.Menu
                    ),
                    title = {
                        Title(title)
                    },
                    navigationIcon = {

                    }
                )
            }
        ) {

        }
    }
}