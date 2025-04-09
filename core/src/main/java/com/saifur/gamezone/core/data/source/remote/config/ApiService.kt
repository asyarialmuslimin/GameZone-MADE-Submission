package com.saifur.gamezone.core.data.source.remote.config


import com.saifur.gamezone.core.BuildConfig
import okhttp3.CertificatePinner
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiService {
    fun getRetrofit() : Endpoint {
        val loggingInterceptor = HttpLoggingInterceptor().apply {
            level = if (BuildConfig.DEBUG) {
                HttpLoggingInterceptor.Level.BODY
            } else {
                HttpLoggingInterceptor.Level.NONE
            }
        }
        val certificatePinner = CertificatePinner.Builder()
            .add(BuildConfig.HOST_NAME, BuildConfig.CERT_PIN)
            .build()

        val okHttpClient = OkHttpClient.Builder()
            .certificatePinner(certificatePinner)
            .addInterceptor(loggingInterceptor)
            .build()

        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(Endpoint::class.java)
    }
}