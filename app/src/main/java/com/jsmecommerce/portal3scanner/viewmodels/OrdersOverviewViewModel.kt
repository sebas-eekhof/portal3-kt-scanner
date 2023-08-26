package com.jsmecommerce.portal3scanner.viewmodels

import android.content.Context
import androidx.compose.runtime.mutableStateMapOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.jsmecommerce.portal3scanner.datasource.portal3api.Portal3Api
import com.jsmecommerce.portal3scanner.datasource.portal3api.models.general.Paginated
import com.jsmecommerce.portal3scanner.datasource.portal3api.models.orders.OrderStatus
import com.jsmecommerce.portal3scanner.datasource.portal3api.models.orders.OverviewOrder
import com.jsmecommerce.portal3scanner.datasource.portal3api.models.stores.Store
import com.jsmecommerce.portal3scanner.utils.put
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class OrdersOverviewViewModel(
    context: Context,
    private val uiViewModel: UiViewModel
): ViewModel() {
    private val api = Portal3Api.getInstance(context)

    private var _orders = MutableLiveData<Paginated<OverviewOrder>?>(null)
    private var _stores = MutableLiveData<List<Store>?>(null)
    private var _statuses = MutableLiveData<List<OrderStatus>?>(null)
    private val _filters = mutableStateMapOf<String, String>()

    val filterStatusId: String? get() = _filters["status_id"]
    val filterStoreId: String? get() = _filters["store_id"]
    val orders: LiveData<Paginated<OverviewOrder>?> get() = _orders
    val stores: LiveData<List<Store>?> get() = _stores
    val statuses: LiveData<List<OrderStatus>?> get() = _statuses

    suspend fun refreshOrders() {
        withContext(Dispatchers.Main) { uiViewModel.setLoading(true) }
        val res = api.orders.all(
            statusId = filterStatusId?.toInt(),
            storeId = filterStoreId?.toInt()
        )
        val body = res.body()
        if(res.isSuccessful && body != null)
            withContext(Dispatchers.Main) { _orders.value = body }
        withContext(Dispatchers.Main) { uiViewModel.setLoading(false) }
    }

    suspend fun refreshStores() {
        withContext(Dispatchers.Main) { uiViewModel.setLoading(true) }
        val res = api.stores.allWithoutPagination()
        val body = res.body()
        if(res.isSuccessful && body != null)
            withContext(Dispatchers.Main) { _stores.value = body }
        withContext(Dispatchers.Main) { uiViewModel.setLoading(false) }
    }

    suspend fun refreshStatusses() {
        withContext(Dispatchers.Main) { uiViewModel.setLoading(true) }
        val res = api.orders.statusses()
        val body = res.body()
        if(res.isSuccessful && body != null)
            withContext(Dispatchers.Main) { _statuses.value = body }
        withContext(Dispatchers.Main) { uiViewModel.setLoading(false) }
    }

    fun setFilterStatusId(statusId: String?) = _filters.put("status_id", statusId)
    fun setFilterStoreId(storeId: String?) = _filters.put("store_id", storeId)

    fun init() {
        CoroutineScope(Dispatchers.IO).launch {
            refreshOrders()
            refreshStores()
            refreshStatusses()
        }
    }

    class Factory(
        private val context: Context,
        private val uiViewModel: UiViewModel
    ) : ViewModelProvider.NewInstanceFactory() {
        override fun <T : ViewModel> create(modelClass: Class<T>): T = OrdersOverviewViewModel(
            context = context,
            uiViewModel = uiViewModel
        ) as T
    }
}