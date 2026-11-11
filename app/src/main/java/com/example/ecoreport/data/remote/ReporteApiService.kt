package com.example.ecoreport.data.remote

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface ReporteApiService {

    @GET("posts")
    suspend fun obtenerReportes(
        @Query("_limit") limite: Int = 10
    ): List<ReporteRemotoDto>

    @POST("posts")
    suspend fun crearReporte(
        @Body reporte: CrearReporteRemotoDto
    ): ReporteRemotoDto

    @PUT("posts/{id}")
    suspend fun actualizarReporte(
        @Path("id") id: Int,
        @Body reporte: CrearReporteRemotoDto
    ): ReporteRemotoDto

    @DELETE("posts/{id}")
    suspend fun eliminarReporte(
        @Path("id") id: Int
    ): Response<Unit>
}
