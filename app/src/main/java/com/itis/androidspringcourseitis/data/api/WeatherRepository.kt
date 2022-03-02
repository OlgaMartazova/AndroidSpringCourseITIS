package com.itis.androidspringcourseitis.data.api

import androidx.viewbinding.BuildConfig
import com.itis.androidspringcourseitis.data.model.info.WeatherInfo
import com.itis.androidspringcourseitis.data.model.list.WeatherList
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private const val BASE_URL = "https://api.openweathermap.org/data/2.5/"

private const val API_KEY = "73d4da5c56a53648c871a6ddcaa16254"
private const val QUERY_API_KEY = "appid"

private const val METRIC = "metric"
private const val QUERY_UNITS = "units"

private const val LANG_CODE = "ru"
private const val QUERY_LANG = "lang"

class WeatherRepository {

    private val api: WeatherApi by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okhttp)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(WeatherApi::class.java)
    }

    private val okhttp: OkHttpClient by lazy {
        OkHttpClient.Builder()
            .addInterceptor(apiKeyInterceptor)
            .addInterceptor(unitsInterceptor)
            .addInterceptor(langInterceptor)
            .also {
                if (BuildConfig.DEBUG) {
                    it.addInterceptor(
                        HttpLoggingInterceptor()
                            .setLevel(
                                HttpLoggingInterceptor.Level.BODY
                            )
                    )
                }
            }
            .build()
    }

    //add api key
    private val apiKeyInterceptor = Interceptor { chain ->
        val original = chain.request()
        val newURL = original.url.newBuilder()
            .addQueryParameter(QUERY_API_KEY, API_KEY)
            .build()

        chain.proceed(
            original.newBuilder()
                .url(newURL)
                .build()
        )
    }


    private val unitsInterceptor = Interceptor { chain ->
        val original = chain.request()
        val newURL = original.url.newBuilder()
            .addQueryParameter(QUERY_UNITS, METRIC)
            .build()

        chain.proceed(
            original.newBuilder()
                .url(newURL)
                .build()
        )
    }

    private val langInterceptor = Interceptor { chain ->
        val original = chain.request()
        val newURL = original.url.newBuilder()
            .addQueryParameter(QUERY_LANG, LANG_CODE)
            .build()

        chain.proceed(
            original.newBuilder()
                .url(newURL)
                .build()
        )
    }


    suspend fun getWeatherByName(city: String): WeatherInfo {
        return api.getWeatherByName(city)
    }

    suspend fun getWeatherById(id: Int): WeatherInfo {
        return api.getWeatherById(id)
    }

    suspend fun getNearCities(latitude: Double, longitude: Double, count: Int): WeatherList {
        return api.getNearCities(latitude, longitude, count)
    }
}
