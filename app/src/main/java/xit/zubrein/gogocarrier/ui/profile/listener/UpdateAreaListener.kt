package xit.zubrein.gogocarrier.ui.profile.listener

import androidx.lifecycle.LiveData
import xit.zubrein.gogocarrier.model.ModelBasicResponse
import xit.zubrein.gogocarrier.model.ModelDashboard

interface UpdateAreaListener {

    fun updateAreaSuccess(model: LiveData<ModelBasicResponse>)

    fun updateAreaFailed(message: String)

}