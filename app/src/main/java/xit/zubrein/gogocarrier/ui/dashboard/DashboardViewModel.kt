package xit.zubrein.gogocarrier.ui.dashboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import xit.zubrein.gogocarrier.ui.dashboard.listener.DashboardListener
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel
@Inject
constructor(private val dashboardRepository: DashboardRepository) : ViewModel() {

    var dashboardListener: DashboardListener? = null

    fun getDashboard(f :String) {
        viewModelScope.launch {
            dashboardListener?.getDashboardStarted()
            val response = dashboardRepository.getDashboard(f)
                .catch { e ->
                    dashboardListener?.getDashboardFailed(e.message!!)
                }.asLiveData()
            dashboardListener?.getDashboardSuccess(response)
        }
    }
}