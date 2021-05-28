package xit.zubrein.gogocarrier.ui.order

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import xit.zubrein.gogocarrier.ui.order.listener.AllOrderListListener
import xit.zubrein.gogocarrier.ui.order.listener.ProductDeliveredListener
import xit.zubrein.gogocarrier.ui.order.listener.ProductPickListener
import xit.zubrein.gogocarrier.ui.order.listener.TodaysOrderListListener
import javax.inject.Inject

@HiltViewModel
class OrderViewModel
@Inject constructor(private val orderRepository: OrderRepository) : ViewModel() {

    var todaysOrderListListener: TodaysOrderListListener? = null
    var allOrderListListener: AllOrderListListener? = null
    var productPickListener: ProductPickListener? = null

    fun getTodaysOrderList() {
        viewModelScope.launch {
            todaysOrderListListener?.todaysOrderReceivedStarted()
            val response = orderRepository.getTodaysOrder()
                .catch { e ->
                    todaysOrderListListener?.todaysOrderReceivedFailed(e.message!!)
                }.asLiveData()

            todaysOrderListListener?.todaysOrderReceivedSuccess(response)
        }
    }

    fun getAllOrderList() {
        viewModelScope.launch {
            allOrderListListener?.allOrderReceivedStarted()
            val response = orderRepository.getAllOrder()
                .catch { e ->
                    allOrderListListener?.allOrderReceivedFailed(e.message!!)
                }.asLiveData()

            allOrderListListener?.allOrderReceivedSuccess(response)
        }
    }

    fun productPicked(orderNo: String) {
        viewModelScope.launch {
            productPickListener?.productPickedStarted()
            val response = orderRepository.productPicked(orderNo)
                .catch {
                    e ->
                    productPickListener?.productPickedFailed(e.message!!)
                }.asLiveData()

            productPickListener?.productPickedSuccess(response)
        }
    }

    fun sendRemarks(orderNo: String,message: String) {
        viewModelScope.launch {
            productPickListener?.sendRemarkStarted()
            val response = orderRepository.sendRemarks(orderNo,message)
                .catch {
                    e ->
                    productPickListener?.sendRemarkFailed(e.message!!)
                }.asLiveData()

            productPickListener?.sendRemarkSuccess(response)
        }
    }


}