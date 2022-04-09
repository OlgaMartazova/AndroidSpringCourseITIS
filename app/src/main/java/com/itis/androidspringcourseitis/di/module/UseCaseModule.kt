package com.itis.androidspringcourseitis.di.module

import com.itis.androidspringcourseitis.domain.repository.WeatherRepository
import com.itis.androidspringcourseitis.domain.usecase.GetNearCitiesUseCase
import com.itis.androidspringcourseitis.domain.usecase.GetWeatherByIdUseCase
import com.itis.androidspringcourseitis.domain.usecase.GetWeatherByNameUseCase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class UseCaseModule {
    @Provides
    @Singleton
    fun provideGetWeatherByNameUseCase(
        weatherRepository: WeatherRepository
    ) = GetWeatherByNameUseCase(weatherRepository)

    @Provides
    @Singleton
    fun provideGetWeatherByIdUseCase(
        weatherRepository: WeatherRepository
    ) = GetWeatherByIdUseCase(weatherRepository)

    @Provides
    @Singleton
    fun provideCitiesCase(
        weatherRepository: WeatherRepository
    ) = GetNearCitiesUseCase(weatherRepository)
}
