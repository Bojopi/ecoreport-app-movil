package com.example.ecoreport.data.remote

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {

    private const val BASE_URL =
        "https://6ae4fb0228393034b08e10be.mockapi.io/"

    val api:
            ReporteApiService by lazy {

        Retrofit.Builder()
            .baseUrl(
                BASE_URL
            )
            .addConverterFactory(
                GsonConverterFactory
                    .create()
            )
            .build()
            .create(
                ReporteApiService::class.java
            )
    }
}
