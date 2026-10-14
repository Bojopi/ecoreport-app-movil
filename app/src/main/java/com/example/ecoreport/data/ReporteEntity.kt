package com.example.ecoreport.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "reportes"
)
data class ReporteEntity(

    @PrimaryKey(
        autoGenerate = true
    )
    val id: Int = 0,

    val titulo: String,

    val descripcion: String,

    val categoria: String,

    val ubicacion: String,

    val estado: String,

    val fecha: String
)