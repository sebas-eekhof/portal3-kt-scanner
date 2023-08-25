package com.jsmecommerce.portal3scanner.viewmodels

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.jsmecommerce.portal3scanner.datasource.portal3api.Portal3Api
import com.jsmecommerce.portal3scanner.datasource.portal3api.models.general.Paginated
import com.jsmecommerce.portal3scanner.datasource.portal3api.models.orders.OrderStatus
import com.jsmecommerce.portal3scanner.datasource.portal3api.models.orders.OverviewOrder
import com.jsmecommerce.portal3scanner.datasource.portal3api.models.stores.Store
import com.jsmecommerce.portal3scanner.models.QueryFilters
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class OrdersOverviewViewModel(context: Context): ViewModel() {
    private val api = Portal3Api.getInstance(context)

    private var filters = QueryFilters()

    private var _orders = MutableLiveData<Paginated<OverviewOrder>?>(null)
    private var _stores = MutableLiveData<List<Store>?>(null)
    private var _statuses = MutableLiveData<List<OrderStatus>?>(null)

    val orders: LiveData<Paginated<OverviewOrder>?> get() = _orders
    val stores: LiveData<List<Store>?> get() = _stores
    val statuses: LiveData<List<OrderStatus>?> get() = _statuses

    suspend fun refreshOrders() {
        val res = api.orders.all()
        val body = res.body()
        if(res.isSuccessful && body != null)
            withContext(Dispatchers.Main) { _orders.value = body }
    }

    suspend fun refreshStores() {
        val res = api.stores.allWithoutPagination()
        val body = res.body()
        if(res.isSuccessful && body != null)
            withContext(Dispatchers.Main) { _stores.value = body }
    }

    suspend fun refreshStatusses() {
        val res = api.orders.statusses()
        val body = res.body()
        if(res.isSuccessful && body != null)
            withContext(Dispatchers.Main) { _statuses.value = body }
    }

    fun setFilterStatusId(statusId: Int?) = filters.put("status_id", statusId)
    fun setFilterStoreId(storeId: Int?) = filters.put("store_id", storeId)

    init {
        println("CREATED ORDEROVERVIEWMODEL")
    }

    fun init() {
        CoroutineScope(Dispatchers.IO).launch {
            refreshOrders()
            refreshStores()
            refreshStatusses()
        }
    }
}