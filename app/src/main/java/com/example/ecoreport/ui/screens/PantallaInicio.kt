package com.example.ecoreport.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.ecoreport.data.local.ReporteEntity

@Composable
fun PantallaInicio(
    reportes: List<ReporteEntity>,
    irRegistro: () -> Unit,
    irReportes: () -> Unit,
    irEstadisticas: () -> Unit,
    irReportesRemotos: () -> Unit
) {
    val pendientes = reportes.count { it.estado == "PENDIENTE" }
    val resueltos = reportes.count { it.estado == "RESUELTO" }

    Column(
        modifier = Modifier.fillMaxSize().padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Text("EcoReport", style = MaterialTheme.typography.headlineLarge, fontWeight = FontWeight.Bold)
        Text("Gestión de reportes ambientales")
        Spacer(Modifier.height(12.dp))
        Text("Total: ${reportes.size}", style = MaterialTheme.typography.titleMedium)
        Text("Pendientes: $pendientes")
        Text("Resueltos: $resueltos")
        Spacer(Modifier.height(14.dp))

        Button(onClick = irRegistro, modifier = Modifier.fillMaxWidth()) {
            Text("NUEVO REPORTE LOCAL")
        }
        Button(onClick = irReportes, modifier = Modifier.fillMaxWidth()) {
            Text("VER REPORTES LOCALES")
        }
        OutlinedButton(onClick = irEstadisticas, modifier = Modifier.fillMaxWidth()) {
            Text("ESTADÍSTICAS")
        }
        OutlinedButton(onClick = irReportesRemotos, modifier = Modifier.fillMaxWidth()) {
            Text("DEMO API / RETROFIT")
        }
    }
}
