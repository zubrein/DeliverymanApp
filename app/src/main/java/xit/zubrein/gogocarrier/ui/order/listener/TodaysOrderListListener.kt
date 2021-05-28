package xit.zubrein.gogocarrier.ui.order.listener

import androidx.lifecycle.LiveData
import xit.zubrein.gogocarrier.model.ModelSingleOrder

interface TodaysOrderListListener {

    fun todaysOrderReceivedStarted()

    fun todaysOrderReceivedSuccess(todays_order_list : LiveData<List<ModelSingleOrder>>)

    fun todaysOrderReceivedFailed(message : String)



}