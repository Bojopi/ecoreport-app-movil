package com.example.ecoreport.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.ecoreport.data.local.ReporteEntity
import com.example.ecoreport.ui.components.TarjetaEstadistica

@Composable
fun PantallaEstadisticas(
    reportes: List<ReporteEntity>,
    volver: () -> Unit
) {
    val pendientes = reportes.count { it.estado == "PENDIENTE" }
    val proceso = reportes.count { it.estado == "EN_PROCESO" }
    val resueltos = reportes.count { it.estado == "RESUELTO" }

    Column(
        modifier = Modifier.fillMaxSize().padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text("Estadísticas", style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.Bold)
        TarjetaEstadistica("Total de reportes", reportes.size)
        TarjetaEstadistica("Pendientes", pendientes)
        TarjetaEstadistica("En proceso", proceso)
        TarjetaEstadistica("Resueltos", resueltos)
        Spacer(Modifier.weight(1f))
        OutlinedButton(onClick = volver, modifier = Modifier.fillMaxWidth()) { Text("VOLVER") }
    }
}
