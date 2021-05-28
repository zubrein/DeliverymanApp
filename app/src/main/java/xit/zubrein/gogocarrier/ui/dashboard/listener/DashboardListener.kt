package xit.zubrein.gogocarrier.ui.dashboard.listener

import androidx.lifecycle.LiveData
import xit.zubrein.gogocarrier.model.ModelDashboard

interface DashboardListener {

    fun getDashboardStarted()

    fun getDashboardSuccess(model: LiveData<ModelDashboard>)

    fun getDashboardFailed(message: String)

}