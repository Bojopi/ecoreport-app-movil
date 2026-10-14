package com.example.ecoreport.navigation

import androidx.compose.runtime.*
import androidx.navigation.compose.*
import com.example.ecoreport.data.ReporteDao
import com.example.ecoreport.data.ReporteEntity
import com.example.ecoreport.ui.screens.*
import com.example.ecoreport.ui_comp.screens.PantallaInicio
import kotlinx.coroutines.launch

@Composable
fun AppNavigation(
    reporteDao: ReporteDao
) {

    val navController =
        rememberNavController()

    val reportes =
        remember {
            mutableStateListOf<
                    ReporteEntity
                    >()
        }

    var reporteSeleccionado by remember {
        mutableStateOf<
                ReporteEntity?
                >(null)
    }

    val scope =
        rememberCoroutineScope()

    suspend fun cargarReportes() {

        val datos =
            reporteDao.obtenerTodos()

        reportes.clear()

        reportes.addAll(
            datos
        )
    }

    LaunchedEffect(Unit) {

        cargarReportes()

    }

    NavHost(
        navController =
            navController,

        startDestination =
            "inicio"
    ) {

        composable(
            "inicio"
        ) {

            PantallaInicio (
                reportes =
                    reportes,

                irRegistro = {
                    navController
                        .navigate(
                            "registro"
                        )
                },

                irReportes = {
                    navController
                        .navigate(
                            "reportes"
                        )
                },

                irEstadisticas = {
                    navController
                        .navigate(
                            "estadisticas"
                        )
                }
            )
        }
        composable(
            "registro"
        ) {

            PantallaRegistro(
                registrarReporte = { reporte ->

                    scope.launch {

                        reporteDao.insertar(
                            reporte
                        )

                        cargarReportes()

                        navController
                            .popBackStack()
                    }
                },

                volver = {

                    navController
                        .popBackStack()
                }
            )
        }
        composable(
            "reportes"
        ) {

            PantallaReportes(
                reportes =
                    reportes,

                seleccionarReporte = { reporte ->

                    reporteSeleccionado =
                        reporte

                    navController.navigate(
                        "detalle"
                    )
                },

                volver = {

                    navController
                        .popBackStack()
                }
            )
        }
        composable(
            "detalle"
        ) {

            PantallaDetalle(
                reporte =
                    reporteSeleccionado,

                eliminarReporte = { reporte ->

                    scope.launch {

                        reporteDao.eliminar(
                            reporte
                        )

                        cargarReportes()

                        reporteSeleccionado =
                            null

                        navController
                            .popBackStack()
                    }
                },

                volver = {

                    navController
                        .popBackStack()
                }
            )
        }
        composable(
            "estadisticas"
        ) {

            PantallaEstadisticas(
                reportes =
                    reportes,

                volver = {

                    navController
                        .popBackStack()
                }
            )
        }
    }
}