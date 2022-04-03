package com.itis.androidspringcourseitis.data

import com.itis.androidspringcourseitis.data.api.WeatherApi
import com.itis.androidspringcourseitis.data.api.mapper.WeatherMapper
import com.itis.androidspringcourseitis.domain.entity.Cities
import com.itis.androidspringcourseitis.domain.entity.Weather
import com.itis.androidspringcourseitis.domain.repository.WeatherRepository

class WeatherRepositoryImpl(
    private val api: WeatherApi,
    private val weatherMapper: WeatherMapper,
) : WeatherRepository {
    override suspend fun getWeatherByName(
        city: String
    ): Weather = weatherMapper.toWeather(api.getWeatherByName(city))

    override suspend fun getWeatherById(
        id: Int
    ): Weather = weatherMapper.toWeather(api.getWeatherById(id))

    override suspend fun getNearCities(
        latitude: Double,
        longitude: Double,
        count: Int
    ): Cities = weatherMapper.toListWeather(api.getNearCities(latitude, longitude, count))
}
