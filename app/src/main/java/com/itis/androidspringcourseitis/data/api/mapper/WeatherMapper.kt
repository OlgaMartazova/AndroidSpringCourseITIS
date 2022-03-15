package com.itis.androidspringcourseitis.data.api.mapper

import com.itis.androidspringcourseitis.data.api.model.WeatherInfo
import com.itis.androidspringcourseitis.data.api.model.WeatherList
import com.itis.androidspringcourseitis.domain.entity.Cities
import com.itis.androidspringcourseitis.domain.entity.Weather

class WeatherMapper {
    fun toWeather(response: WeatherInfo): Weather = Weather(
        id = response.id,
        name = response.name,
        latitude = response.coord.lat,
        longitude = response.coord.lon,
        temp = response.main.temp,
        icon = response.weather[0].icon,
        desc = response.weather[0].description,
        feelsLike = response.main.feelsLike,
        humidity = response.main.humidity,
        windSpeed = response.wind.speed,
        windDir = response.wind.deg,
        timezone = response.timezone,
        sunrise = response.sys.sunrise,
        sunset = response.sys.sunset
    )

    fun toListWeather(response: WeatherList): Cities = Cities(
        list = response.weatherList.map { weatherInfo ->  toWeather(weatherInfo)}
    )
}
