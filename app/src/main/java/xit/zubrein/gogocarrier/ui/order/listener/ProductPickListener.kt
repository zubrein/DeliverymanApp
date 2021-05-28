package xit.zubrein.gogocarrier.ui.order.listener

import androidx.lifecycle.LiveData
import xit.zubrein.gogocarrier.model.ModelBasicResponse
import xit.zubrein.gogocarrier.model.ModelSingleOrder

interface ProductPickListener {

    fun productPickedStarted()

    fun productPickedSuccess(model : LiveData<ModelBasicResponse>)

    fun productPickedFailed(message : String)

    fun sendRemarkStarted()

    fun sendRemarkSuccess(model : LiveData<ModelBasicResponse>)

    fun sendRemarkFailed(message : String)

    
    
    
}