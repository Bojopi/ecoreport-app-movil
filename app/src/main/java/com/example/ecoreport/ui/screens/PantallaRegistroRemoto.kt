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
import com.example.ecoreport.model.CategoriaReporte

@Composable
fun PantallaRegistroRemoto(
    guardar: (String, String, String, String) -> Unit,
    volver: () -> Unit
) {
    var titulo by remember { mutableStateOf("") }
    var descripcion by remember { mutableStateOf("") }
    var categoria by remember { mutableStateOf(CategoriaReporte.LUMINARIA.etiqueta) }
    var ubicacion by remember { mutableStateOf("") }
    var error by remember { mutableStateOf("") }

    Column(Modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(20.dp)) {
        Text("POST: nuevo reporte remoto", style = MaterialTheme.typography.headlineMedium)
        Text("La API pública devolverá una respuesta simulada.", style = MaterialTheme.typography.bodySmall)
        Spacer(Modifier.height(14.dp))
        OutlinedTextField(titulo, { titulo = it }, label = { Text("Título") }, modifier = Modifier.fillMaxWidth())
        Spacer(Modifier.height(8.dp))
        OutlinedTextField(descripcion, { descripcion = it }, label = { Text("Descripción") }, minLines = 3, modifier = Modifier.fillMaxWidth())
        Spacer(Modifier.height(8.dp))
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
        OutlinedTextField(ubicacion, { ubicacion = it }, label = { Text("Ubicación") }, modifier = Modifier.fillMaxWidth())
        if (error.isNotBlank()) Text(error, color = MaterialTheme.colorScheme.error)
        Spacer(Modifier.height(14.dp))
        Button(
            onClick = {
                error = when {
                    titulo.isBlank() -> "Ingrese un título."
                    descripcion.isBlank() -> "Ingrese una descripción."
                    ubicacion.isBlank() -> "Ingrese una ubicación."
                    else -> ""
                }
                if (error.isEmpty()) guardar(titulo.trim(), descripcion.trim(), categoria, ubicacion.trim())
            },
            modifier = Modifier.fillMaxWidth()
        ) { Text("EJECUTAR POST") }
        OutlinedButton(onClick = volver, modifier = Modifier.fillMaxWidth()) { Text("CANCELAR") }
    }
}
