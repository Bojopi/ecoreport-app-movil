package com.example.ecoreport.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.ecoreport.data.remote.ReporteRemotoDto

@Composable
fun PantallaReportesRemotos(
    reportes: List<ReporteRemotoDto>,
    cargando: Boolean,
    error: String?,
    mensaje: String?,
    nuevoReporte: () -> Unit,
    recargar: () -> Unit,
    actualizar: (ReporteRemotoDto) -> Unit,
    eliminar: (Int) -> Unit,
    limpiarMensaje: () -> Unit,
    volver: () -> Unit
) {
    Column(Modifier.fillMaxSize().padding(20.dp)) {
        Text("Demo API con Retrofit", style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.Bold)
        Text("JSONPlaceholder: las escrituras se simulan y no persisten en el servidor.", style = MaterialTheme.typography.bodySmall)
        Spacer(Modifier.height(10.dp))

        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            Button(onClick = nuevoReporte) { Text("POST NUEVO") }
            OutlinedButton(onClick = recargar) { Text("GET RECARGAR") }
        }

        if (mensaje != null) {
            Spacer(Modifier.height(8.dp))
            Card(onClick = limpiarMensaje) {
                Text(mensaje, modifier = Modifier.padding(12.dp))
            }
        }
        if (error != null) {
            Spacer(Modifier.height(8.dp))
            Text(error, color = MaterialTheme.colorScheme.error)
        }
        Spacer(Modifier.height(10.dp))

        if (cargando && reportes.isEmpty()) {
            Column(Modifier.weight(1f).fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
                CircularProgressIndicator()
            }
        } else {
            LazyColumn(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                items(reportes, key = { it.id }) { reporte ->
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(16.dp),
                        elevation = CardDefaults.cardElevation(defaultElevation = 3.dp)
                    ) {
                        Column(Modifier.padding(16.dp)) {
                            Text(reporte.tituloVisible, fontWeight = FontWeight.Bold)
                            Text(reporte.descripcionVisible, maxLines = 3)
                            Text("Categoría: ${reporte.categoriaVisible}")
                            Text("Estado demo: ${reporte.estadoVisible}")
                            Spacer(Modifier.height(8.dp))
                            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                                Button(onClick = { actualizar(reporte) }) { Text("PUT") }
                                OutlinedButton(onClick = { eliminar(reporte.id) }) { Text("DELETE") }
                            }
                        }
                    }
                }
            }
        }
        OutlinedButton(onClick = volver, modifier = Modifier.fillMaxWidth()) { Text("VOLVER") }
    }
}
