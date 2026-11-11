package com.example.ecoreport.data.remote

data class ReporteRemotoDto(
    val id: Int = 0,
    val userId: Int? = null,
    val title: String? = null,
    val body: String? = null,
    val titulo: String? = null,
    val descripcion: String? = null,
    val categoria: String? = null,
    val ubicacion: String? = null,
    val estado: String? = null
) {
    val tituloVisible: String
        get() = titulo ?: title ?: "Reporte remoto #$id"

    val descripcionVisible: String
        get() = descripcion ?: body ?: "Sin descripción"

    val categoriaVisible: String
        get() = categoria ?: "GENERAL"

    val ubicacionVisible: String
        get() = ubicacion ?: "Servidor de demostración"

    val estadoVisible: String
        get() = estado ?: "PENDIENTE"
}
