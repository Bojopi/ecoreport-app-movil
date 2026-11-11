package com.example.ecoreport.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.FilterChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.ecoreport.data.local.ReporteEntity
import com.example.ecoreport.model.CategoriaReporte

@Composable
fun PantallaRegistro(
    registrarReporte: (ReporteEntity) -> Unit,
    volver: () -> Unit
) {
    var titulo by remember { mutableStateOf("") }
    var descripcion by remember { mutableStateOf("") }
    var categoria by remember { mutableStateOf(CategoriaReporte.LUMINARIA.etiqueta) }
    var ubicacion by remember { mutableStateOf("") }
    var fecha by remember { mutableStateOf("") }
    var error by remember { mutableStateOf("") }

    Column(
        modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(20.dp)
    ) {
        Text("Nuevo reporte local", style = MaterialTheme.typography.headlineMedium)
        Spacer(Modifier.height(16.dp))

        OutlinedTextField(titulo, { titulo = it }, label = { Text("Título") }, modifier = Modifier.fillMaxWidth())
        Spacer(Modifier.height(10.dp))
        OutlinedTextField(
            descripcion,
            { descripcion = it },
            label = { Text("Descripción") },
            minLines = 3,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(Modifier.height(12.dp))
        Text("Categoría")
        Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
            CategoriaReporte.entries.forEach { item ->
                FilterChip(
                    selected = categoria == item.etiqueta,
                    onClick = { categoria = item.etiqueta },
                    label = { Text(item.etiqueta) }
                )
            }
        }
        Spacer(Modifier.height(10.dp))
        OutlinedTextField(ubicacion, { ubicacion = it }, label = { Text("Ubicación") }, modifier = Modifier.fillMaxWidth())
        Spacer(Modifier.height(10.dp))
        OutlinedTextField(fecha, { fecha = it }, label = { Text("Fecha (ej. 06/11/2026)") }, modifier = Modifier.fillMaxWidth())

        if (error.isNotBlank()) {
            Spacer(Modifier.height(10.dp))
            Text(error, color = MaterialTheme.colorScheme.error)
        }

        Spacer(Modifier.height(18.dp))
        Button(
            onClick = {
                error = when {
                    titulo.isBlank() -> "Ingrese un título."
                    descripcion.isBlank() -> "Ingrese una descripción."
                    ubicacion.isBlank() -> "Ingrese una ubicación."
                    fecha.isBlank() -> "Ingrese una fecha."
                    else -> ""
                }
                if (error.isEmpty()) {
                    registrarReporte(
                        ReporteEntity(
                            titulo = titulo.trim(),
                            descripcion = descripcion.trim(),
                            categoria = categoria,
                            ubicacion = ubicacion.trim(),
                            estado = "PENDIENTE",
                            fecha = fecha.trim()
                        )
                    )
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) { Text("GUARDAR") }

        OutlinedButton(onClick = volver, modifier = Modifier.fillMaxWidth()) { Text("CANCELAR") }
    }
}
