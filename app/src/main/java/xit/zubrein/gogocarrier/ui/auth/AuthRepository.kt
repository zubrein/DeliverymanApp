package xit.zubrein.gogocarrier.ui.auth

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import okhttp3.MultipartBody
import okhttp3.RequestBody
import xit.zubrein.gogocarrier.model.*
import xit.zubrein.gogocarrier.network.apis.AuthApiService

import javax.inject.Inject

class AuthRepository
@Inject
constructor(private val authApiService: AuthApiService) {

    fun getAuthResponse(
        msisdn: String,
        password: String
    ): Flow<ModelAuth> = flow {
        val response = authApiService.getAuthResponse(
            msisdn, password
        )
        emit(response)
    }.flowOn(Dispatchers.IO)

    fun logout() : Flow<ModelBasicResponse> = flow {
        val response = authApiService.logout()
        emit(response)
    }.flowOn(Dispatchers.IO)

    fun getWallet() : Flow<ModelWallet> = flow {
        val response = authApiService.getWallet()
        emit(response)
    }.flowOn(Dispatchers.IO)

    fun send_token(token :String) :Flow<ModelBasicResponse> = flow {
        val response = authApiService.send_token(token)
        emit(response)
    }.flowOn(Dispatchers.IO)

    fun uploadImage(description : RequestBody,
                    user_image : MultipartBody.Part,
                    user_nid_front : MultipartBody.Part,
                    user_nid_back : MultipartBody.Part
    ) : Flow<ModelBasicResponse> = flow {
        val response = authApiService.fileUpload(description,user_image,user_nid_front,user_nid_back)
        emit(response)
    }.flowOn(Dispatchers.IO)

    fun send_otp(msisdn:String):Flow<ModelAuth> = flow {
        val response = authApiService.sent_otp(msisdn)
        emit(response)
    }.flowOn(Dispatchers.IO)

    fun submit_otp(msisdn:String,otp_token:String,otp:String) : Flow<ModelOTPResponse> = flow {
        val response = authApiService.submit_otp(msisdn,otp_token,otp)
        emit(response)
    }.flowOn(Dispatchers.IO)


}