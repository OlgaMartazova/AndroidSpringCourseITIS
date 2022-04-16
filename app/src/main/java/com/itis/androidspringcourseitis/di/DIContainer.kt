package com.itis.androidspringcourseitis.di

import android.content.Context
import androidx.viewbinding.BuildConfig
import com.itis.androidspringcourseitis.data.WeatherRepositoryImpl
import com.itis.androidspringcourseitis.data.api.WeatherApi
import com.itis.androidspringcourseitis.data.api.mapper.WeatherMapper
import com.itis.androidspringcourseitis.domain.converter.CountryNameConverter
import com.itis.androidspringcourseitis.domain.converter.DateConverter
import com.itis.androidspringcourseitis.domain.converter.WindConverter
import com.itis.androidspringcourseitis.domain.repository.WeatherRepository
import com.itis.androidspringcourseitis.domain.usecase.GetNearCitiesUseCase
import com.itis.androidspringcourseitis.domain.usecase.GetWeatherByIdUseCase
import com.itis.androidspringcourseitis.domain.usecase.GetWeatherByNameUseCase
import kotlinx.coroutines.Dispatchers
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

private const val LANG_CODE = "en"
private const val QUERY_LANG = "lang"

class DIContainer(
    private val context: Context
) {

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

    //temp in °C
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

    //lang English
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

    private val api: WeatherApi by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okhttp)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(WeatherApi::class.java)
    }

    private val weatherMapper: WeatherMapper = WeatherMapper(
        windConverter = WindConverter(),
        dateConverter = DateConverter(),
        countryNameConverter = CountryNameConverter(context)
    )
    private val weatherRepository: WeatherRepository = WeatherRepositoryImpl(
        api = api,
        weatherMapper = weatherMapper
    )

    val getNearCitiesUseCase: GetNearCitiesUseCase = GetNearCitiesUseCase(
        weatherRepository = weatherRepository,
        dispatcher = Dispatchers.Default
    )

    val getWeatherByIdUseCase: GetWeatherByIdUseCase = GetWeatherByIdUseCase(
        weatherRepository = weatherRepository,
        dispatcher = Dispatchers.Default
    )

    val getWeatherByNameUseCase: GetWeatherByNameUseCase = GetWeatherByNameUseCase(
        weatherRepository = weatherRepository,
        dispatcher = Dispatchers.Default
    )
}

