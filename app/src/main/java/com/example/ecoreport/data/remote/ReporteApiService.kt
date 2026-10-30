package com.example.ecoreport.data.remote

import retrofit2.http.GET

interface ReporteApiService {

    @GET("reportes")
    suspend fun obtenerReportes():
            List<ReporteDto>
}
