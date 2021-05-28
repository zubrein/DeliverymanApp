package xit.zubrein.gogocarrier.ui.order

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import xit.zubrein.gogocarrier.model.ModelAllOrder
import xit.zubrein.gogocarrier.model.ModelBasicResponse
import xit.zubrein.gogocarrier.model.ModelSingleOrder
import xit.zubrein.gogocarrier.network.apis.OrderApiService
import javax.inject.Inject

class OrderRepository @Inject constructor(private val orderApiService: OrderApiService) {

    fun getTodaysOrder(): Flow<List<ModelSingleOrder>> = flow {
        val response = orderApiService.getTodaysOrder()
        emit(response)
    }.flowOn(Dispatchers.IO)

    fun getAllOrder(): Flow<List<ModelAllOrder>> = flow {
        val response = orderApiService.getAllOrderList()
        emit(response)
    }.flowOn(Dispatchers.IO)

    fun productPicked(orderNo: String) : Flow<ModelBasicResponse> = flow {
        val response = orderApiService.productPicked(orderNo)
        emit(response)
    }.flowOn(Dispatchers.IO)

    fun sendRemarks(orderNo: String,message: String): Flow<ModelBasicResponse> = flow {
        val response = orderApiService.sendRemark(orderNo,message)
        emit(response)
    }.flowOn(Dispatchers.IO)




}