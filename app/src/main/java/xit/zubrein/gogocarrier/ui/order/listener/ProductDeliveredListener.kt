package xit.zubrein.gogocarrier.ui.order.listener

import androidx.lifecycle.LiveData
import xit.zubrein.gogocarrier.model.ModelBasicResponse
import xit.zubrein.gogocarrier.model.ModelSingleOrder

interface ProductDeliveredListener {

    fun productDeliveredStarted()

    fun productDeliveredSuccess(model : LiveData<ModelBasicResponse>)

    fun productDeliveredFailed(message : String)
    
}