package xit.zubrein.gogocarrier.network.apis

import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST
import xit.zubrein.gogocarrier.model.ModelDashboard
import xit.zubrein.gogocarrier.model.ModelSingleOrder
import xit.zubrein.gogocarrier.network.data.ApiConstants
import xit.zubrein.gogocarrier.network.data.ApiKeys

interface StatisticsApiService {

    @FormUrlEncoded
    @POST(ApiConstants.DASHBOARD)
    suspend fun getDashboard(
        @Field(ApiKeys.FILTER) filter : String
    ) : ModelDashboard

}