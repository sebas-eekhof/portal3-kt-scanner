package com.jsmecommerce.portal3scanner.viewmodels

import androidx.lifecycle.ViewModel
import com.jsmecommerce.portal3scanner.models.general.QueryFilters

class OrdersOverviewViewModel: ViewModel() {
    private var filters = QueryFilters()

    fun setFilterStatusId(statusId: Int?) = filters.put("status_id", statusId)
    fun setFilterStoreId(storeId: Int?) = filters.put("store_id", storeId)
}