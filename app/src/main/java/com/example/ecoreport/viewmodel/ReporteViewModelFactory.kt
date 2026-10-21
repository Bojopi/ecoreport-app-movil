package com.example.ecoreport.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.ecoreport.repository.ReporteRepository

class ReporteViewModelFactory(
    private val repository: ReporteRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(
        modelClass: Class<T>
    ): T {

        if (
            modelClass.isAssignableFrom(
                ReporteViewModel::class.java
            )
        ) {

            @Suppress("UNCHECKED_CAST")

            return ReporteViewModel(
                repository
            ) as T
        }

        throw IllegalArgumentException(
            "ViewModel desconocido"
        )
    }
}