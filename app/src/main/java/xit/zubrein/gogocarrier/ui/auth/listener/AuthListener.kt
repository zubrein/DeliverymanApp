package xit.zubrein.gogocarrier.ui.auth.listener

import androidx.lifecycle.LiveData
import xit.zubrein.gogocarrier.model.ModelAuth

interface AuthListener {

    fun onSuccess(modelAuth: LiveData<ModelAuth>)

    fun onFailure(message: String)

}