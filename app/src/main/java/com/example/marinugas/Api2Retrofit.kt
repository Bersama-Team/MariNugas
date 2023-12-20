package com.example.marinugas

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class Api2Retrofit {

    val endpoint: Api2Endpoint
        get(){
            val interceptor = HttpLoggingInterceptor()
            interceptor.level = HttpLoggingInterceptor.Level.BODY

            val client = OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .build()

            val retrofit = Retrofit.Builder()
                .baseUrl("http://192.168.100.204/MariNugas/crud/")
                .client( client )
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            return retrofit.create(Api2Endpoint::class.java)
        }
}