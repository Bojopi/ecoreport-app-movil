package com.example.ecoreport.ui_comp.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.ecoreport.data.ReporteEntity

@Composable
fun PantallaDetalle(
    reporte: ReporteEntity?,
    actualizarReporte: (ReporteEntity) -> Unit,
    eliminarReporte: (ReporteEntity) -> Unit,
    volver: () -> Unit
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp)
    ) {

        Text(
            text = "Detalle del reporte",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold
        )

        Spacer(
            modifier = Modifier.height(20.dp)
        )

        if (reporte == null) {

            Text(
                text = "No existe un reporte seleccionado."
            )

        } else {

            Card(
                modifier = Modifier.fillMaxWidth(),

                shape = RoundedCornerShape(20.dp),

                elevation = CardDefaults.cardElevation(
                    defaultElevation = 6.dp
                )
            ) {

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp)
                ) {

                    Text(
                        text = reporte.titulo,
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(
                        modifier = Modifier.height(20.dp)
                    )

                    Text(
                        text = "Descripción",
                        fontWeight = FontWeight.Bold
                    )

                    Text(
                        text = reporte.descripcion
                    )

                    Spacer(
                        modifier = Modifier.height(14.dp)
                    )

                    Text(
                        text = "Categoría",
                        fontWeight = FontWeight.Bold
                    )

                    Text(
                        text = reporte.categoria
                    )

                    Spacer(
                        modifier = Modifier.height(14.dp)
                    )

                    Text(
                        text = "Ubicación",
                        fontWeight = FontWeight.Bold
                    )

                    Text(
                        text = reporte.ubicacion
                    )

                    Spacer(
                        modifier = Modifier.height(14.dp)
                    )

                    Text(
                        text = "Fecha",
                        fontWeight = FontWeight.Bold
                    )

                    Text(
                        text = reporte.fecha
                    )

                    Spacer(
                        modifier = Modifier.height(14.dp)
                    )

                    Text(
                        text = "Estado",
                        fontWeight = FontWeight.Bold
                    )

                    Text(
                        text = reporte.estado
                    )

                    Spacer(
                        modifier = Modifier.height(24.dp)
                    )

                    Button(
                        onClick = {
                            val nuevoEstado = when (reporte.estado) {
                                "PENDIENTE" -> "EN_PROCESO"
                                "EN_PROCESO" -> "RESUELTO"
                                else -> "RESUELTO"
                            }

                            val reporteActualizado = reporte.copy(estado = nuevoEstado)

                            actualizarReporte(reporteActualizado)
                        },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(text = "CAMBIAR ESTADO")
                    }
                    Spacer(
                        modifier = Modifier.height(10.dp)
                    )

                    Button(
                        onClick = {
                            eliminarReporte(reporte)
                        },

                        modifier = Modifier.fillMaxWidth()
                    ) {

                        Text(
                            text = "ELIMINAR REPORTE"
                        )
                    }
                }
            }
        }

        Spacer(
            modifier = Modifier.height(20.dp)
        )

        OutlinedButton(
            onClick = volver,
            modifier = Modifier.fillMaxWidth()
        ) {

            Text(
                text = "VOLVER"
            )
        }
    }
}