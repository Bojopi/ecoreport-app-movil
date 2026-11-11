package com.example.ecoreport.ui.screens

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
import com.example.ecoreport.data.local.ReporteEntity

@Composable
fun PantallaDetalle(
    reporte: ReporteEntity?,
    actualizarReporte: (ReporteEntity) -> Unit,
    eliminarReporte: (ReporteEntity) -> Unit,
    volver: () -> Unit
) {
    Column(Modifier.fillMaxSize().padding(20.dp)) {
        Text("Detalle del reporte", style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.Bold)
        Spacer(Modifier.height(16.dp))
        if (reporte == null) {
            Text("No existe un reporte seleccionado.")
        } else {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 5.dp)
            ) {
                Column(Modifier.padding(20.dp)) {
                    Text(reporte.titulo, style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold)
                    Spacer(Modifier.height(12.dp))
                    Text("Descripción", fontWeight = FontWeight.Bold); Text(reporte.descripcion)
                    Spacer(Modifier.height(8.dp))
                    Text("Categoría", fontWeight = FontWeight.Bold); Text(reporte.categoria)
                    Spacer(Modifier.height(8.dp))
                    Text("Ubicación", fontWeight = FontWeight.Bold); Text(reporte.ubicacion)
                    Spacer(Modifier.height(8.dp))
                    Text("Fecha", fontWeight = FontWeight.Bold); Text(reporte.fecha)
                    Spacer(Modifier.height(8.dp))
                    Text("Estado", fontWeight = FontWeight.Bold); Text(reporte.estado)
                    Spacer(Modifier.height(18.dp))

                    if (reporte.estado != "RESUELTO") {
                        Button(
                            onClick = {
                                val nuevo = when (reporte.estado) {
                                    "PENDIENTE" -> "EN_PROCESO"
                                    "EN_PROCESO" -> "RESUELTO"
                                    else -> "RESUELTO"
                                }
                                actualizarReporte(reporte.copy(estado = nuevo))
                            },
                            modifier = Modifier.fillMaxWidth()
                        ) { Text("CAMBIAR ESTADO") }
                    }
                    OutlinedButton(
                        onClick = { eliminarReporte(reporte) },
                        modifier = Modifier.fillMaxWidth()
                    ) { Text("ELIMINAR REPORTE") }
                }
            }
        }
        Spacer(Modifier.height(16.dp))
        OutlinedButton(onClick = volver, modifier = Modifier.fillMaxWidth()) { Text("VOLVER") }
    }
}
