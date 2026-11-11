package com.example.ecoreport.ui.screens

import androidx.compose.foundation.clickable
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
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.ecoreport.data.local.ReporteEntity
import com.example.ecoreport.model.CategoriaReporte

@Composable
fun PantallaReportes(
    reportes: List<ReporteEntity>,
    categoriaSeleccionada: String?,
    seleccionarCategoria: (String?) -> Unit,
    seleccionarReporte: (ReporteEntity) -> Unit,
    volver: () -> Unit
) {
    Column(modifier = Modifier.fillMaxSize().padding(20.dp)) {
        Text("Reportes locales", style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.Bold)
        Spacer(Modifier.height(12.dp))
        Text("Filtro ejecutado por Room con SELECT ... WHERE", style = MaterialTheme.typography.bodySmall)
        Spacer(Modifier.height(8.dp))

        Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
            FilterChip(
                selected = categoriaSeleccionada == null,
                onClick = { seleccionarCategoria(null) },
                label = { Text("TODOS") }
            )
            CategoriaReporte.entries.forEach { item ->
                FilterChip(
                    selected = categoriaSeleccionada == item.etiqueta,
                    onClick = { seleccionarCategoria(item.etiqueta) },
                    label = { Text(item.etiqueta) }
                )
            }
        }

        Spacer(Modifier.height(12.dp))
        if (reportes.isEmpty()) {
            Text("No existen reportes para este filtro.")
            Spacer(Modifier.weight(1f))
        } else {
            LazyColumn(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                items(reportes, key = { it.id }) { reporte ->
                    Card(
                        modifier = Modifier.fillMaxWidth().clickable { seleccionarReporte(reporte) },
                        shape = RoundedCornerShape(16.dp),
                        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                    ) {
                        Column(Modifier.padding(16.dp)) {
                            Text(reporte.titulo, fontWeight = FontWeight.Bold)
                            Text("Categoría: ${reporte.categoria}")
                            Text("Ubicación: ${reporte.ubicacion}")
                            Text("Estado: ${reporte.estado}")
                            Text("Fecha: ${reporte.fecha}")
                        }
                    }
                }
            }
        }
        OutlinedButton(onClick = volver, modifier = Modifier.fillMaxWidth()) { Text("VOLVER") }
    }
}
