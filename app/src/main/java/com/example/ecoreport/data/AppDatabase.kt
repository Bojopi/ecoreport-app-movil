package com.example.ecoreport.data

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [
        ReporteEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase :
    RoomDatabase() {

    abstract fun reporteDao():
            ReporteDao
}