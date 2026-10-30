package com.example.ecoreport.data.remote

import retrofit2.http.GET

interface ReporteApiService {

    @GET("Api-jaldin")
    suspend fun obtenerReportes():
            List<ReporteDto>
}
