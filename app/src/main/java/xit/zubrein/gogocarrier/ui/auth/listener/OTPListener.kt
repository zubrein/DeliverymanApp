package xit.zubrein.gogocarrier.ui.auth.listener

import androidx.lifecycle.LiveData
import xit.zubrein.gogocarrier.model.ModelOTPResponse

interface OTPListener {
    fun onOTPStarted()

    fun onOTPSuccess(response: LiveData<ModelOTPResponse>)

    fun onOTPFailure(message: String)
}