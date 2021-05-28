package xit.zubrein.gogocarrier.network

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import xit.zubrein.gogocarrier.BuildConfig
import xit.zubrein.gogocarrier.Utils.pref.PrefKeys
import xit.zubrein.gogocarrier.Utils.pref.PreferenceManager
import xit.zubrein.gogocarrier.network.apis.AreaApiService
import xit.zubrein.gogocarrier.network.apis.AuthApiService
import xit.zubrein.gogocarrier.network.apis.OrderApiService
import xit.zubrein.gogocarrier.network.apis.StatisticsApiService
import xit.zubrein.gogocarrier.network.data.ApiConstants
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ApiClient {

    @Provides
    @Singleton
    fun retrofit(
        preferenceManager: PreferenceManager
    ): Retrofit = Retrofit.Builder()
        .baseUrl(ApiConstants.BASE_URL)
        .client(
            OkHttpClient.Builder()
                .addInterceptor {
                    it.proceed(
                        it.request().newBuilder().also {
                            it.addHeader(
                                "Authorization", "Bearer ${
                                    preferenceManager.getString(
                                        PrefKeys.AUTH_TOKEN
                                    )
                                }"
                            )
                        }.build()
                    )
                }
                .also { client ->
                    if (BuildConfig.DEBUG) {
                        val logging = HttpLoggingInterceptor()
                        logging.setLevel(HttpLoggingInterceptor.Level.BODY)
                        client.addInterceptor(logging)
                    }
                }.build()
        )
        .addConverterFactory(GsonConverterFactory.create())
        .build()


    //Provides Api Interfaces

    @Provides
    @Singleton
    fun authApiService(retrofit: Retrofit) : AuthApiService =
        retrofit.create(AuthApiService::class.java)

    @Provides
    @Singleton
    fun orderApiService(retrofit: Retrofit) : OrderApiService =
        retrofit.create(OrderApiService::class.java)

    @Provides
    @Singleton
    fun statisticsApiService(retrofit: Retrofit) : StatisticsApiService =
        retrofit.create(StatisticsApiService::class.java)

    @Provides
    @Singleton
    fun AreaApiService(retrofit: Retrofit) : AreaApiService =
        retrofit.create(AreaApiService::class.java)



}