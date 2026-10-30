package com.example.ecoreport.ui_comp.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.ecoreport.viewmodel.EstadoReportesRemotos

@Composable
fun PantallaReportesRemotos(
    estado: EstadoReportesRemotos,
    reintentar: () -> Unit,
    volver: () -> Unit
) {
    Column(Modifier.fillMaxSize().padding(20.dp)) {
        Text("Reportes remotos", style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.Bold)
        Spacer(Modifier.height(16.dp))

        Column(Modifier.weight(1f)) {
            when (estado) {
                EstadoReportesRemotos.Cargando -> CircularProgressIndicator()
                EstadoReportesRemotos.Error -> {
                    Text("No se pudieron cargar los reportes. Comprueba tu conexión e inténtalo de nuevo.")
                    Button(onClick = reintentar) { Text("REINTENTAR") }
                }
                is EstadoReportesRemotos.Exito -> {
                    if (estado.reportes.isEmpty()) {
                        Text("No hay reportes remotos disponibles.")
                    } else {
                        LazyColumn(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                            items(estado.reportes) { reporte ->
                                Card(Modifier.fillMaxWidth()) {
                                    Column(Modifier.padding(16.dp)) {
                                        Text(reporte.titulo, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                                        Text(reporte.descripcion)
                                        Text("Categoría: ${reporte.categoria}")
                                        Text("Ubicación: ${reporte.ubicacion}")
                                        Text("Estado: ${reporte.estado}")
                                        reporte.fecha?.let { Text("Fecha: $it") }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        Spacer(Modifier.height(16.dp))
        OutlinedButton(onClick = volver, modifier = Modifier.fillMaxWidth()) { Text("VOLVER") }
    }
}
