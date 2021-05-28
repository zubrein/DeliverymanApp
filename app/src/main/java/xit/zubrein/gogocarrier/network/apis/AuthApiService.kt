package xit.zubrein.gogocarrier.network.apis

import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.*
import xit.zubrein.gogocarrier.model.*
import xit.zubrein.gogocarrier.network.data.ApiConstants
import xit.zubrein.gogocarrier.network.data.ApiKeys

interface AuthApiService {

    //Login
    @FormUrlEncoded
    @POST(ApiConstants.LOGIN)
    suspend fun getAuthResponse(
        @Field(ApiKeys.MSISDN) msisdn: String,
        @Field(ApiKeys.PASSWORD) password: String
    ): ModelAuth

    @POST(ApiConstants.LOGOUT)
    suspend fun logout(): ModelBasicResponse

    @FormUrlEncoded
    @POST(ApiConstants.SEND_TOKEN)
    suspend fun send_token(
        @Field(ApiKeys.FIREBASE_TOKEN) token: String
    ): ModelBasicResponse

    @Multipart
    @POST(ApiConstants.REGISTRATION)
    suspend fun fileUpload(
        @Part("sender_information") description: RequestBody?,
        @Part user_image: MultipartBody.Part?,
        @Part user_nid_front: MultipartBody.Part?,
        @Part user_nid_back: MultipartBody.Part?
    ): ModelBasicResponse

    @FormUrlEncoded
    @POST(ApiConstants.SENT_OTP)
    suspend fun sent_otp(
        @Field(ApiKeys.MSISDN) msisdn:String
    ): ModelAuth

    @POST(ApiConstants.WALLET)
    suspend fun getWallet(): ModelWallet

    @FormUrlEncoded
    @POST(ApiConstants.SUBMIT_OTP)
    suspend fun submit_otp(
        @Field(ApiKeys.MSISDN) msisdn:String,
        @Field(ApiKeys.OTP_TOKEN) otp_token:String,
        @Field(ApiKeys.OTP) otp:String
    ): ModelOTPResponse

}