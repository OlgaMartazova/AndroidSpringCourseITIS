package com.itis.androidspringcourseitis.domain.repository

import com.itis.androidspringcourseitis.domain.entity.Cities
import com.itis.androidspringcourseitis.domain.entity.Weather


interface WeatherRepository {

    suspend fun getWeatherByName(city: String): Weather

    suspend fun getWeatherById(id: Int): Weather

    suspend fun getNearCities(latitude: Double, longitude: Double, count: Int): Cities
}
