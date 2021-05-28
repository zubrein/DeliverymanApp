package xit.zubrein.gogocarrier.ui.auth

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import okhttp3.MultipartBody
import okhttp3.RequestBody
import xit.zubrein.gogocarrier.model.ModelAuth
import xit.zubrein.gogocarrier.model.ModelBasicResponse
import xit.zubrein.gogocarrier.model.ModelOTPResponse
import xit.zubrein.gogocarrier.model.ModelWallet
import xit.zubrein.gogocarrier.ui.auth.listener.AuthListener
import xit.zubrein.gogocarrier.ui.auth.listener.LogoutListener
import xit.zubrein.gogocarrier.ui.auth.listener.OTPListener
import xit.zubrein.gogocarrier.ui.auth.listener.RegisterListener
import javax.inject.Inject

@HiltViewModel
class AuthViewModel
@Inject
constructor(private val authRepository: AuthRepository): ViewModel() {

    private val TAG = "AuthViewModel"
    var authListener : AuthListener? = null
    var logoutListener : LogoutListener? = null
    var registerListener : RegisterListener? = null
    var otpListener: OTPListener? = null

    fun getAuthResponse(
        msisdn: String,
        password: String
    ){
        val response = authRepository.getAuthResponse(
            msisdn, password
        ).catch { e->
            Log.d(TAG, "getAuthResponse: $e")
            authListener?.onFailure(e.message!!)
        }.asLiveData()
        authListener?.onSuccess(response)

    }

    fun sendToken(token: String): LiveData<ModelBasicResponse> {
        val response: LiveData<ModelBasicResponse> = authRepository.send_token(token)
            .catch { e ->
                Log.d(TAG, "firebaseToken: ${e.message}")
            }.asLiveData()

        return response
    }

    fun logout() {
        logoutListener?.onStarted()
        val response: LiveData<ModelBasicResponse> = authRepository.logout()
            .catch { e ->
                logoutListener?.onFailure(e.message.toString())
            }.asLiveData()
        logoutListener?.onSuccess(response)
    }

    fun getWallet() : LiveData<ModelWallet> {
        return authRepository.getWallet()
            .catch { e ->
                Log.d(TAG, "getWallet: ${e.message}")
            }.asLiveData()
    }

    fun doRegistration(
        description : RequestBody,
        user_image : MultipartBody.Part,
        user_nid_front : MultipartBody.Part,
        user_nid_back : MultipartBody.Part
    ){
        registerListener?.onRegisterStarted()
        val response : LiveData<ModelBasicResponse> = authRepository.uploadImage(description, user_image,user_nid_front,user_nid_back)
            .catch {
                    e->
                registerListener?.onRegisterFailure("${e.message}")
            }.asLiveData()

        registerListener?.onRegisterSuccess(response)

    }

    fun sendOtp(msisdn: String): LiveData<ModelAuth> {
        val response: LiveData<ModelAuth> = authRepository.send_otp(msisdn)
            .catch { e ->
                Log.d(TAG, "sendOtp: ${e.message}")
            }.asLiveData()

        return response
    }

    fun submitOTP(msisdn:String,otp_token:String,otp:String) {
        otpListener?.onOTPStarted()
        val response: LiveData<ModelOTPResponse> = authRepository.submit_otp(msisdn,otp_token,otp)
            .catch { e ->
                otpListener?.onOTPFailure("${e.message}")
            }.asLiveData()

        otpListener?.onOTPSuccess(response)

    }




}