package xit.zubrein.gogocarrier.network.apis

import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST
import xit.zubrein.gogocarrier.model.ModelAllOrder
import xit.zubrein.gogocarrier.model.ModelBasicResponse
import xit.zubrein.gogocarrier.model.ModelSingleOrder
import xit.zubrein.gogocarrier.network.data.ApiConstants
import xit.zubrein.gogocarrier.network.data.ApiKeys

interface OrderApiService {


    @POST(ApiConstants.TODAYS_ORDER)
    suspend fun getTodaysOrder() : List<ModelSingleOrder>

    @POST(ApiConstants.All_ORDER_LIST)
    suspend fun getAllOrderList() : List<ModelAllOrder>

    @FormUrlEncoded
    @POST(ApiConstants.PICKED_PRODUCT_FROM_WAREHOUSE)
    suspend fun productPicked(
        @Field(ApiKeys.ORDER_NO) order_no:String
    ):ModelBasicResponse

    @FormUrlEncoded
    @POST(ApiConstants.REMARKS)
    suspend fun sendRemark(
        @Field(ApiKeys.ORDER_NO) order_no: String,
        @Field(ApiKeys.MESSAGE) message: String
    ): ModelBasicResponse




}