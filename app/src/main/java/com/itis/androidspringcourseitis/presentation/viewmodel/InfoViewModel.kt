package com.itis.androidspringcourseitis.presentation.viewmodel

import androidx.lifecycle.*
import com.itis.androidspringcourseitis.domain.entity.Weather
import com.itis.androidspringcourseitis.domain.usecase.GetWeatherByIdUseCase
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.launch

class InfoViewModel @AssistedInject constructor(
    private val getWeatherByIdUseCase: GetWeatherByIdUseCase,
    @Assisted private val idCity: Int
) : ViewModel() {

    private var _weather: MutableLiveData<Result<Weather>> = MutableLiveData()
    val weather: LiveData<Result<Weather>> = _weather

    fun onGetWeatherByIdClick() {
        viewModelScope.launch {
            try {
                val weather = getWeatherByIdUseCase(idCity)
                _weather.value = Result.success(weather)
            } catch (ex: Exception) {
                _weather.value = Result.failure(ex)
            }
        }
    }

    @AssistedFactory
    interface WeatherViewModelFactory {
        fun create(idCity: Int): InfoViewModel
    }

    @Suppress("UNCHECKED_CAST")
    companion object {
        fun provideFactory(
            assistedFactory: WeatherViewModelFactory,
            idCity: Int
        ): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>): T =
                assistedFactory.create(idCity) as T
        }
    }
}
