package xit.zubrein.gogocarrier.ui.auth.listener

import androidx.lifecycle.LiveData
import xit.zubrein.gogocarrier.model.ModelAuth
import xit.zubrein.gogocarrier.model.ModelBasicResponse

interface RegisterListener {

    fun onRegisterStarted()

    fun onRegisterSuccess(model: LiveData<ModelBasicResponse>)

    fun onRegisterFailure(message: String)

}