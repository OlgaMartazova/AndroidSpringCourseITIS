package com.itis.androidspringcourseitis.di.module

import com.itis.androidspringcourseitis.data.WeatherRepositoryImpl
import com.itis.androidspringcourseitis.domain.repository.WeatherRepository
import dagger.Binds
import dagger.Module

@Module
interface RepositoryModule {
    @Binds
    fun weatherRepository(
        weatherRepositoryImpl: WeatherRepositoryImpl
    ): WeatherRepository
}
