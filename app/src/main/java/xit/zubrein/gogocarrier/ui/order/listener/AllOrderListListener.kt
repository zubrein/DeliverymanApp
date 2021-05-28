package xit.zubrein.gogocarrier.ui.order.listener

import androidx.lifecycle.LiveData
import xit.zubrein.gogocarrier.model.ModelAllOrder

interface AllOrderListListener {

    fun allOrderReceivedStarted()

    fun allOrderReceivedSuccess(all_order_list : LiveData<List<ModelAllOrder>>)

    fun allOrderReceivedFailed(message : String)

}