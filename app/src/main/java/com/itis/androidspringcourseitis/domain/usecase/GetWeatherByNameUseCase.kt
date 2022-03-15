package com.itis.androidspringcourseitis.domain.usecase

import com.itis.androidspringcourseitis.data.api.model.WeatherInfo
import com.itis.androidspringcourseitis.domain.entity.Weather
import com.itis.androidspringcourseitis.domain.repository.WeatherRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GetWeatherByNameUseCase (
    private val weatherRepository: WeatherRepository,
    private val dispatcher: CoroutineDispatcher = Dispatchers.Main
) {
    suspend operator fun invoke(
        city: String
    ): Weather {
        return withContext(dispatcher) {
            weatherRepository.getWeatherByName(
                city
            )
        }
    }
}