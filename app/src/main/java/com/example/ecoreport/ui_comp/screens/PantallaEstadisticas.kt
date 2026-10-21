package com.example.ecoreport.ui_comp.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
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
fun PantallaEstadisticas(
    reportes: List<ReporteEntity>,
    volver: () -> Unit
) {

    val totalReportes =
        reportes.size

    val pendientes =
        reportes.count {
            it.estado == "PENDIENTE"
        }

    val enProceso =
        reportes.count {
            it.estado == "EN_PROCESO"
        }

    val resueltos =
        reportes.count {
            it.estado == "RESUELTO"
        }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),

        verticalArrangement =
            Arrangement.spacedBy(12.dp)
    ) {

        Text(
            text = "Estadísticas",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold
        )

        Text(
            text = "Resumen general de reportes ambientales"
        )

        Spacer(
            modifier = Modifier.height(10.dp)
        )

        TarjetaEstadistica(
            titulo = "Total de reportes",
            valor = totalReportes
        )

        TarjetaEstadistica(
            titulo = "Pendientes",
            valor = pendientes
        )

        TarjetaEstadistica(
            titulo = "En proceso",
            valor = enProceso
        )

        TarjetaEstadistica(
            titulo = "Resueltos",
            valor = resueltos
        )

        Spacer(
            modifier = Modifier.weight(1f)
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


@Composable
fun TarjetaEstadistica(
    titulo: String,
    valor: Int
) {

    Card(
        modifier = Modifier.fillMaxWidth(),

        shape = RoundedCornerShape(
            16.dp
        ),

        elevation =
            CardDefaults.cardElevation(
                defaultElevation = 4.dp
            )
    ) {

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(18.dp)
        ) {

            Text(
                text = titulo,
                style = MaterialTheme.typography.titleMedium
            )

            Spacer(
                modifier = Modifier.height(6.dp)
            )

            Text(
                text = valor.toString(),
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold
            )
        }
    }
}