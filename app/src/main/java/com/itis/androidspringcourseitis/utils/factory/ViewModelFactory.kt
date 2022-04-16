package com.itis.androidspringcourseitis.utils.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.itis.androidspringcourseitis.di.DIContainer
import com.itis.androidspringcourseitis.presentation.viewmodel.InfoViewModel
import com.itis.androidspringcourseitis.presentation.viewmodel.ListViewModel

class ViewModelFactory (
    private val di: DIContainer
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        when {
            modelClass.isAssignableFrom(ListViewModel::class.java) ->
                ListViewModel(
                    di.getWeatherByNameUseCase,
                    di.getNearCitiesUseCase
                ) as? T ?: throw IllegalArgumentException("Unknown ViewModel class")
            modelClass.isAssignableFrom(InfoViewModel::class.java) ->
                InfoViewModel(
                    di.getWeatherByIdUseCase
                ) as? T ?: throw IllegalArgumentException("Unknown ViewModel class")
            else ->
                throw IllegalArgumentException("Unknown ViewModel class")
        }
}
