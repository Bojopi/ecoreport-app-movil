package com.example.ecoreport.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ecoreport.data.ReporteEntity
import com.example.ecoreport.data.remote.ReporteDto
import com.example.ecoreport.repository.ReporteRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.WhileSubscribed
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Job

sealed interface EstadoReportesRemotos {
    data object Cargando : EstadoReportesRemotos
    data class Exito(val reportes: List<ReporteDto>) : EstadoReportesRemotos
    data object Error : EstadoReportesRemotos
}

class ReporteViewModel (
    private val repository: ReporteRepository
): ViewModel() {

    private val _categoriaSeleccionada = MutableStateFlow<String?>(null)

    private val _estadoReportesRemotos = MutableStateFlow<EstadoReportesRemotos>(EstadoReportesRemotos.Cargando)
    val estadoReportesRemotos: StateFlow<EstadoReportesRemotos> = _estadoReportesRemotos
    private var consultaRemota: Job? = null

    fun cargarReportesRemotos() {
        consultaRemota?.cancel()
        consultaRemota = viewModelScope.launch {
            _estadoReportesRemotos.value = EstadoReportesRemotos.Cargando
            try {
                _estadoReportesRemotos.value = EstadoReportesRemotos.Exito(repository.obtenerReportesRemotos())
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                _estadoReportesRemotos.value = EstadoReportesRemotos.Error
            }
        }
    }

    val categoriaSeleccionada: StateFlow<String?> = _categoriaSeleccionada
    val reportes:
    StateFlow<List<ReporteEntity>> =
        _categoriaSeleccionada
            .flatMapLatest {
                categoria ->
                repository.obtenerPorCategoria(categoria)
            }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted
                    .WhileSubscribed(5000),
                initialValue = emptyList()
            )



    fun insertar(reporte: ReporteEntity) {
        viewModelScope.launch {
            repository.insertar(reporte)
        }
    }

    fun eliminar(reporte: ReporteEntity) {
        viewModelScope.launch {
            repository.eliminar(reporte)
        }
    }

    fun actualizar(reporte: ReporteEntity) {
        viewModelScope.launch {
            repository.actualizar(reporte)
        }
    }

    fun seleccionarCategoria(categoria: String?) {
        _categoriaSeleccionada.value = categoria
    }
}
