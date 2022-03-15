package com.itis.androidspringcourseitis.domain.usecase

import com.itis.androidspringcourseitis.domain.entity.Cities
import com.itis.androidspringcourseitis.domain.repository.WeatherRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GetNearCitiesUseCase(
    private val weatherRepository: WeatherRepository,
    private val dispatcher: CoroutineDispatcher = Dispatchers.Main
) {
    suspend operator fun invoke(
        latitude: Double,
        longitude: Double,
        count: Int
    ): Cities {
        return withContext(dispatcher) {
            weatherRepository.getNearCities(
                latitude,
                longitude,
                count
            )
        }
    }
}