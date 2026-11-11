package com.example.ecoreport.data.remote

data class CrearReporteRemotoDto(
    val userId: Int = 1,
    val title: String,
    val body: String,
    val titulo: String,
    val descripcion: String,
    val categoria: String,
    val ubicacion: String,
    val estado: String
)
