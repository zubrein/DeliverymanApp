package xit.zubrein.gogocarrier.ui.dashboard

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import xit.zubrein.gogocarrier.model.ModelDashboard
import xit.zubrein.gogocarrier.network.apis.StatisticsApiService
import javax.inject.Inject

class DashboardRepository @Inject constructor(private val statisticsApiService: StatisticsApiService){

    fun getDashboard(f : String) : Flow<ModelDashboard> = flow {
        val response = statisticsApiService.getDashboard(f)
        emit(response)
    }.flowOn(Dispatchers.IO)

}