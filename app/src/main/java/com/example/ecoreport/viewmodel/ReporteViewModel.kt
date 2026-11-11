package com.example.ecoreport.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ecoreport.data.local.ReporteEntity
import com.example.ecoreport.data.remote.CrearReporteRemotoDto
import com.example.ecoreport.data.remote.ReporteRemotoDto
import com.example.ecoreport.repository.ReporteRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class ReporteViewModel(
    private val repository: ReporteRepository
) : ViewModel() {

    // ---------- Local / Room ----------
    val todosReportes: StateFlow<List<ReporteEntity>> =
        repository.obtenerTodos().stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = emptyList()
        )

    private val _categoriaSeleccionada = MutableStateFlow<String?>(null)
    val categoriaSeleccionada: StateFlow<String?> = _categoriaSeleccionada

    val reportesFiltrados: StateFlow<List<ReporteEntity>> =
        _categoriaSeleccionada
            .flatMapLatest { categoria ->
                repository.obtenerPorCategoria(categoria)
            }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = emptyList()
            )

    fun seleccionarCategoria(categoria: String?) {
        _categoriaSeleccionada.value = categoria
    }

    fun insertar(reporte: ReporteEntity) {
        viewModelScope.launch { repository.insertar(reporte) }
    }

    fun actualizar(reporte: ReporteEntity) {
        viewModelScope.launch { repository.actualizar(reporte) }
    }

    fun eliminar(reporte: ReporteEntity) {
        viewModelScope.launch { repository.eliminar(reporte) }
    }

    // ---------- Remoto / Retrofit ----------
    private val _reportesRemotos = MutableStateFlow<List<ReporteRemotoDto>>(emptyList())
    val reportesRemotos: StateFlow<List<ReporteRemotoDto>> = _reportesRemotos

    private val _cargandoRemoto = MutableStateFlow(false)
    val cargandoRemoto: StateFlow<Boolean> = _cargandoRemoto

    private val _errorRemoto = MutableStateFlow<String?>(null)
    val errorRemoto: StateFlow<String?> = _errorRemoto

    private val _mensajeRemoto = MutableStateFlow<String?>(null)
    val mensajeRemoto: StateFlow<String?> = _mensajeRemoto

    fun limpiarMensajeRemoto() {
        _mensajeRemoto.value = null
    }

    fun cargarReportesRemotos() {
        viewModelScope.launch {
            _cargandoRemoto.value = true
            _errorRemoto.value = null
            try {
                _reportesRemotos.value = repository.obtenerReportesRemotos()
            } catch (e: Exception) {
                _errorRemoto.value = e.message ?: "No se pudieron cargar los datos remotos."
            } finally {
                _cargandoRemoto.value = false
            }
        }
    }

    fun crearReporteRemoto(
        titulo: String,
        descripcion: String,
        categoria: String,
        ubicacion: String
    ) {
        viewModelScope.launch {
            _cargandoRemoto.value = true
            _errorRemoto.value = null
            try {
                val nuevo = CrearReporteRemotoDto(
                    title = titulo,
                    body = descripcion,
                    titulo = titulo,
                    descripcion = descripcion,
                    categoria = categoria,
                    ubicacion = ubicacion,
                    estado = "PENDIENTE"
                )
                val creado = repository.crearReporteRemoto(nuevo)
                _reportesRemotos.value = listOf(creado) + _reportesRemotos.value
                _mensajeRemoto.value =
                    "POST ejecutado correctamente. JSONPlaceholder simula la creación y no la persiste."
            } catch (e: Exception) {
                _errorRemoto.value = e.message ?: "No se pudo crear el reporte remoto."
            } finally {
                _cargandoRemoto.value = false
            }
        }
    }

    fun actualizarEstadoRemoto(reporte: ReporteRemotoDto) {
        viewModelScope.launch {
            _cargandoRemoto.value = true
            _errorRemoto.value = null
            try {
                val actual = reporte.estadoVisible
                val nuevoEstado = when (actual) {
                    "PENDIENTE" -> "EN_PROCESO"
                    "EN_PROCESO" -> "RESUELTO"
                    else -> "RESUELTO"
                }

                val body = CrearReporteRemotoDto(
                    userId = reporte.userId ?: 1,
                    title = reporte.tituloVisible,
                    body = reporte.descripcionVisible,
                    titulo = reporte.tituloVisible,
                    descripcion = reporte.descripcionVisible,
                    categoria = reporte.categoriaVisible,
                    ubicacion = reporte.ubicacionVisible,
                    estado = nuevoEstado
                )

                // JSONPlaceholder permite PUT para sus posts existentes (1..100).
                if (reporte.id !in 1..100) {
                    _errorRemoto.value =
                        "El elemento creado por POST es simulado y no existe realmente para un PUT posterior."
                    return@launch
                }

                val actualizado = repository.actualizarReporteRemoto(reporte.id, body)
                val normalizado = actualizado.copy(
                    titulo = actualizado.titulo ?: reporte.tituloVisible,
                    descripcion = actualizado.descripcion ?: reporte.descripcionVisible,
                    categoria = actualizado.categoria ?: reporte.categoriaVisible,
                    ubicacion = actualizado.ubicacion ?: reporte.ubicacionVisible,
                    estado = actualizado.estado ?: nuevoEstado
                )
                _reportesRemotos.value = _reportesRemotos.value.map {
                    if (it.id == reporte.id) normalizado else it
                }
                _mensajeRemoto.value = "PUT ejecutado correctamente (respuesta simulada por JSONPlaceholder)."
            } catch (e: Exception) {
                _errorRemoto.value = e.message ?: "No se pudo actualizar el reporte remoto."
            } finally {
                _cargandoRemoto.value = false
            }
        }
    }

    fun eliminarReporteRemoto(id: Int) {
        viewModelScope.launch {
            _cargandoRemoto.value = true
            _errorRemoto.value = null
            try {
                if (id !in 1..100) {
                    _reportesRemotos.value = _reportesRemotos.value.filterNot { it.id == id }
                    _mensajeRemoto.value = "Elemento simulado eliminado de la lista local de demostración."
                } else if (repository.eliminarReporteRemoto(id)) {
                    _reportesRemotos.value = _reportesRemotos.value.filterNot { it.id == id }
                    _mensajeRemoto.value = "DELETE ejecutado correctamente (JSONPlaceholder no persiste el borrado)."
                } else {
                    _errorRemoto.value = "La API rechazó la eliminación."
                }
            } catch (e: Exception) {
                _errorRemoto.value = e.message ?: "No se pudo eliminar el reporte remoto."
            } finally {
                _cargandoRemoto.value = false
            }
        }
    }
}
