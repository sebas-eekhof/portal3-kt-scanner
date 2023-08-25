package com.jsmecommerce.portal3scanner.viewmodels

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.jsmecommerce.portal3scanner.datasource.portal3api.Portal3Api
import com.jsmecommerce.portal3scanner.datasource.portal3api.models.general.Paginated
import com.jsmecommerce.portal3scanner.datasource.portal3api.models.orders.Order
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class OrderViewModel(
    context: Context,
    private val orderId: Int
) : ViewModel() {
    val api = Portal3Api.getInstance(context)

    private var _order = MutableLiveData<Order?>(null)

    val order: LiveData<Order?> get() = _order

    suspend fun refreshOrder() {
        val res = api.orders.get(orderId = orderId)
        val body = res.body()
        if(res.isSuccessful && body != null)
            withContext(Dispatchers.Main) { _order.value = body }
    }

    init {
        CoroutineScope(Dispatchers.IO).launch {
            refreshOrder()
        }
    }
}