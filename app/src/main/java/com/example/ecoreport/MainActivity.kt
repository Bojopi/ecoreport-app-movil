package com.example.ecoreport

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.room.Room
import com.example.ecoreport.data.AppDatabase
import com.example.ecoreport.navigation.AppNavigation

class MainActivity :
    ComponentActivity() {

    override fun onCreate(
        savedInstanceState: Bundle?
    ) {

        super.onCreate(
            savedInstanceState
        )

        val database =
            Room.databaseBuilder(
                applicationContext,
                AppDatabase::class.java,
                "ecoreport_database"
            ).build()

        val reporteDao =
            database.reporteDao()

        setContent {

            AppNavigation(
                reporteDao =
                    reporteDao
            )
        }
    }
}