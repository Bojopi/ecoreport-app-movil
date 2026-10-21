package com.example.ecoreport.ui_comp.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
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
import com.example.ecoreport.data.ReporteEntity

@Composable
fun PantallaRegistro(
    registrarReporte: (ReporteEntity) -> Unit,
    volver: () -> Unit
) {

    var titulo by remember {
        mutableStateOf("")
    }

    var descripcion by remember {
        mutableStateOf("")
    }

    var categoria by remember {
        mutableStateOf("")
    }

    var ubicacion by remember {
        mutableStateOf("")
    }

    var fecha by remember {
        mutableStateOf("")
    }

    var mensajeError by remember {
        mutableStateOf("")
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(
                rememberScrollState()
            )
            .padding(20.dp)
    ) {

        Text(
            text = "Nuevo reporte",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(
            modifier = Modifier.height(20.dp)
        )

        OutlinedTextField(
            value = titulo,
            onValueChange = {
                titulo = it
            },
            label = {
                Text("Título")
            },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(
            modifier = Modifier.height(12.dp)
        )

        OutlinedTextField(
            value = descripcion,
            onValueChange = {
                descripcion = it
            },
            label = {
                Text("Descripción")
            },
            modifier = Modifier.fillMaxWidth(),
            minLines = 3
        )

        Spacer(
            modifier = Modifier.height(12.dp)
        )

        OutlinedTextField(
            value = categoria,
            onValueChange = {
                categoria = it
            },
            label = {
                Text("Categoría")
            },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(
            modifier = Modifier.height(12.dp)
        )

        OutlinedTextField(
            value = ubicacion,
            onValueChange = {
                ubicacion = it
            },
            label = {
                Text("Ubicación")
            },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(
            modifier = Modifier.height(12.dp)
        )

        OutlinedTextField(
            value = fecha,
            onValueChange = {
                fecha = it
            },
            label = {
                Text("Fecha")
            },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(
            modifier = Modifier.height(12.dp)
        )

        if (mensajeError.isNotEmpty()) {

            Text(
                text = mensajeError,
                color = MaterialTheme.colorScheme.error
            )

            Spacer(
                modifier = Modifier.height(12.dp)
            )
        }

        Button(
            onClick = {

                when {

                    titulo.isBlank() -> {

                        mensajeError =
                            "Debe ingresar un título."
                    }

                    descripcion.isBlank() -> {

                        mensajeError =
                            "Debe ingresar una descripción."
                    }

                    categoria.isBlank() -> {

                        mensajeError =
                            "Debe ingresar una categoría."
                    }

                    ubicacion.isBlank() -> {

                        mensajeError =
                            "Debe ingresar una ubicación."
                    }

                    fecha.isBlank() -> {

                        mensajeError =
                            "Debe ingresar una fecha."
                    }

                    else -> {

                        val nuevoReporte =
                            ReporteEntity(
                                titulo = titulo.trim(),
                                descripcion = descripcion.trim(),
                                categoria = categoria.trim(),
                                ubicacion = ubicacion.trim(),
                                estado = "PENDIENTE",
                                fecha = fecha.trim()
                            )

                        registrarReporte(
                            nuevoReporte
                        )
                    }
                }
            },

            modifier = Modifier.fillMaxWidth()
        ) {

            Text(
                text = "GUARDAR REPORTE"
            )
        }

        Spacer(
            modifier = Modifier.height(10.dp)
        )

        OutlinedButton(
            onClick = volver,
            modifier = Modifier.fillMaxWidth()
        ) {

            Text(
                text = "CANCELAR"
            )
        }
    }
}