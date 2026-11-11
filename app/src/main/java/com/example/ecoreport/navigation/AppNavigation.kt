package com.example.ecoreport.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.ecoreport.data.local.ReporteEntity
import com.example.ecoreport.repository.ReporteRepository
import com.example.ecoreport.ui.screens.PantallaDetalle
import com.example.ecoreport.ui.screens.PantallaEstadisticas
import com.example.ecoreport.ui.screens.PantallaInicio
import com.example.ecoreport.ui.screens.PantallaRegistro
import com.example.ecoreport.ui.screens.PantallaRegistroRemoto
import com.example.ecoreport.ui.screens.PantallaReportes
import com.example.ecoreport.ui.screens.PantallaReportesRemotos
import com.example.ecoreport.viewmodel.ReporteViewModel
import com.example.ecoreport.viewmodel.ReporteViewModelFactory

@Composable
fun AppNavigation(repository: ReporteRepository) {
    val navController = rememberNavController()
    val factory = remember(repository) { ReporteViewModelFactory(repository) }
    val viewModel: ReporteViewModel = viewModel(factory = factory)

    val todosReportes by viewModel.todosReportes.collectAsStateWithLifecycle()
    val reportesFiltrados by viewModel.reportesFiltrados.collectAsStateWithLifecycle()
    val categoriaSeleccionada by viewModel.categoriaSeleccionada.collectAsStateWithLifecycle()
    val reportesRemotos by viewModel.reportesRemotos.collectAsStateWithLifecycle()
    val cargandoRemoto by viewModel.cargandoRemoto.collectAsStateWithLifecycle()
    val errorRemoto by viewModel.errorRemoto.collectAsStateWithLifecycle()
    val mensajeRemoto by viewModel.mensajeRemoto.collectAsStateWithLifecycle()

    var reporteSeleccionado by remember { mutableStateOf<ReporteEntity?>(null) }

    NavHost(navController = navController, startDestination = "inicio") {
        composable("inicio") {
            PantallaInicio(
                reportes = todosReportes,
                irRegistro = { navController.navigate("registro") },
                irReportes = { navController.navigate("reportes") },
                irEstadisticas = { navController.navigate("estadisticas") },
                irReportesRemotos = { navController.navigate("remotos") }
            )
        }

        composable("registro") {
            PantallaRegistro(
                registrarReporte = {
                    viewModel.insertar(it)
                    navController.popBackStack()
                },
                volver = { navController.popBackStack() }
            )
        }

        composable("reportes") {
            PantallaReportes(
                reportes = reportesFiltrados,
                categoriaSeleccionada = categoriaSeleccionada,
                seleccionarCategoria = viewModel::seleccionarCategoria,
                seleccionarReporte = {
                    reporteSeleccionado = it
                    navController.navigate("detalle")
                },
                volver = {
                    viewModel.seleccionarCategoria(null)
                    navController.popBackStack()
                }
            )
        }

        composable("detalle") {
            PantallaDetalle(
                reporte = reporteSeleccionado,
                actualizarReporte = {
                    viewModel.actualizar(it)
                    reporteSeleccionado = it
                },
                eliminarReporte = {
                    viewModel.eliminar(it)
                    reporteSeleccionado = null
                    navController.popBackStack()
                },
                volver = { navController.popBackStack() }
            )
        }

        composable("estadisticas") {
            PantallaEstadisticas(
                reportes = todosReportes,
                volver = { navController.popBackStack() }
            )
        }

        composable("remotos") {
            LaunchedEffect(Unit) {
                if (reportesRemotos.isEmpty()) viewModel.cargarReportesRemotos()
            }
            PantallaReportesRemotos(
                reportes = reportesRemotos,
                cargando = cargandoRemoto,
                error = errorRemoto,
                mensaje = mensajeRemoto,
                nuevoReporte = { navController.navigate("registro_remoto") },
                recargar = viewModel::cargarReportesRemotos,
                actualizar = viewModel::actualizarEstadoRemoto,
                eliminar = viewModel::eliminarReporteRemoto,
                limpiarMensaje = viewModel::limpiarMensajeRemoto,
                volver = { navController.popBackStack() }
            )
        }

        composable("registro_remoto") {
            PantallaRegistroRemoto(
                guardar = { titulo, descripcion, categoria, ubicacion ->
                    viewModel.crearReporteRemoto(titulo, descripcion, categoria, ubicacion)
                    navController.popBackStack()
                },
                volver = { navController.popBackStack() }
            )
        }
    }
}
