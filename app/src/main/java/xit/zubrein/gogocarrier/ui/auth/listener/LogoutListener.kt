package xit.zubrein.gogocarrier.ui.auth.listener

import androidx.lifecycle.LiveData
import xit.zubrein.gogocarrier.model.ModelBasicResponse

interface LogoutListener {
    fun onStarted()

    fun onSuccess(response: LiveData<ModelBasicResponse>)

    fun onFailure(message: String)
}