package com.itis.androidspringcourseitis.data.api

import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {
    @GET("weather?units=metric&lang=ru")
    suspend fun getWeatherByName(@Query("q") city: String)

    @GET("find?units=metric&lang=ru")
    suspend fun getNearCities(
        @Query("lat") latitude: Double,
        @Query("lon") longitude: Double,
        @Query("cnt") count: Int
    )

    @GET("weather?units=metric&lang=ru")
    suspend fun getWeatherById(@Query("id") id: Int)
}
