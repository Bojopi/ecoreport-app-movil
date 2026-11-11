package com.example.ecoreport.data.remote

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {

    // API pública de demostración. GET es real; POST/PUT/DELETE se simulan
    // pero JSONPlaceholder no persiste esos cambios en el servidor.
    private const val BASE_URL = "https://jsonplaceholder.typicode.com/"

    val api: ReporteApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ReporteApiService::class.java)
    }
}
