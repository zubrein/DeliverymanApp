package xit.zubrein.gogocarrier.ui.profile

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import xit.zubrein.gogocarrier.model.ModelArea
import xit.zubrein.gogocarrier.model.ModelAreaIDList
import xit.zubrein.gogocarrier.model.ModelBasicResponse
import xit.zubrein.gogocarrier.network.apis.AreaApiService
import javax.inject.Inject

class ProfileRepository
@Inject
constructor(private val profileApiService: AreaApiService) {

    fun getAreaList() : Flow<List<ModelArea>> = flow {
        val response = profileApiService.getAreaList()
        emit(response)
    }.flowOn(Dispatchers.IO)

    fun getSelectedAreaList() : Flow<List<ModelArea>> = flow {
        val response = profileApiService.getSelectedAreaList()
        emit(response)
    }.flowOn(Dispatchers.IO)



    fun update_area(model : ModelAreaIDList) : Flow<ModelBasicResponse> = flow {
        val response = profileApiService.update_area(model)
        emit(response)
    }.flowOn(Dispatchers.IO)

}