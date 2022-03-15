package com.itis.androidspringcourseitis.data.api.model

import com.google.gson.annotations.SerializedName

class WeatherList (
    @SerializedName("list")
    val weatherList: List<WeatherInfo>
)