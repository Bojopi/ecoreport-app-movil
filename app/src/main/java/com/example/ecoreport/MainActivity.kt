package com.example.ecoreport

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.room.Room
import com.example.ecoreport.data.local.AppDatabase
import com.example.ecoreport.data.remote.RetrofitClient
import com.example.ecoreport.navigation.AppNavigation
import com.example.ecoreport.repository.ReporteRepository
import com.example.ecoreport.ui.theme.EcoReportTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val database = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            "ecoreport_database"
        ).build()

        val repository = ReporteRepository(
            reporteDao = database.reporteDao(),
            api = RetrofitClient.api
        )

        setContent {
            EcoReportTheme {
                AppNavigation(repository = repository)
            }
        }
    }
}
