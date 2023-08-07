package com.jsmecommerce.portal3scanner.utils

import android.content.Context
import androidx.navigation.NavController
import com.jsmecommerce.portal3scanner.models.Page

class AppController(val applicationContext: Context, val page: Page, val setPage: (page: Page) -> Unit, val navController: NavController) {
    fun setPage(title: String, backRoute: String? = null) {
        setPage(Page(title = title, backRoute = backRoute))
    }
}