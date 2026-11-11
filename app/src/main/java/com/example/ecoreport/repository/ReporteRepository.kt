package com.example.ecoreport.repository

import com.example.ecoreport.data.local.ReporteDao
import com.example.ecoreport.data.local.ReporteEntity
import com.example.ecoreport.data.remote.CrearReporteRemotoDto
import com.example.ecoreport.data.remote.ReporteApiService
import com.example.ecoreport.data.remote.ReporteRemotoDto
import kotlinx.coroutines.flow.Flow

class ReporteRepository(
    private val reporteDao: ReporteDao,
    private val api: ReporteApiService
) {
    // Local / Room
    fun obtenerTodos(): Flow<List<ReporteEntity>> = reporteDao.obtenerTodos()

    fun obtenerPorCategoria(categoria: String?): Flow<List<ReporteEntity>> =
        reporteDao.obtenerPorCategoria(categoria)

    suspend fun insertar(reporte: ReporteEntity) = reporteDao.insertar(reporte)

    suspend fun actualizar(reporte: ReporteEntity) = reporteDao.actualizar(reporte)

    suspend fun eliminar(reporte: ReporteEntity) = reporteDao.eliminar(reporte)

    // Remoto / Retrofit
    suspend fun obtenerReportesRemotos(): List<ReporteRemotoDto> =
        api.obtenerReportes()

    suspend fun crearReporteRemoto(reporte: CrearReporteRemotoDto): ReporteRemotoDto =
        api.crearReporte(reporte)

    suspend fun actualizarReporteRemoto(
        id: Int,
        reporte: CrearReporteRemotoDto
    ): ReporteRemotoDto = api.actualizarReporte(id, reporte)

    suspend fun eliminarReporteRemoto(id: Int): Boolean =
        api.eliminarReporte(id).isSuccessful
}
