package com.itis.androidspringcourseitis.data.api

import com.itis.androidspringcourseitis.data.model.info.WeatherInfo
import com.itis.androidspringcourseitis.data.model.list.WeatherList
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {
    @GET("weather?lang=ru")
    suspend fun getWeatherByName(@Query("q") city: String): WeatherInfo

    @GET("find?lang=ru")
    suspend fun getNearCities(
        @Query("lat") latitude: Double,
        @Query("lon") longitude: Double,
        @Query("cnt") count: Int
    ): WeatherList

    @GET("weather?lang=ru")
    suspend fun getWeatherById(@Query("id") id: Int): WeatherInfo
}
