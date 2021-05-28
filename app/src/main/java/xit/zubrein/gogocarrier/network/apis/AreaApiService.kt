package xit.zubrein.gogocarrier.network.apis

import retrofit2.http.Body
import retrofit2.http.POST
import xit.zubrein.gogocarrier.model.ModelArea
import xit.zubrein.gogocarrier.model.ModelAreaIDList
import xit.zubrein.gogocarrier.model.ModelBasicResponse
import xit.zubrein.gogocarrier.network.data.ApiConstants

interface AreaApiService {

    @POST(ApiConstants.UPDATE_AREA)
    suspend fun update_area(
        @Body area_id: ModelAreaIDList
    ): ModelBasicResponse

    @POST(ApiConstants.AREA)
    suspend fun getAreaList(): List<ModelArea>

    @POST(ApiConstants.GET_SELECTED_AREA)
    suspend fun getSelectedAreaList(): List<ModelArea>



}