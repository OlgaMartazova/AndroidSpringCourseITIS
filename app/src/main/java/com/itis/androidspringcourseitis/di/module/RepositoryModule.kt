package com.itis.androidspringcourseitis.di.module

import com.itis.androidspringcourseitis.data.WeatherRepositoryImpl
import com.itis.androidspringcourseitis.domain.repository.WeatherRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {
    @Binds
    fun weatherRepository(
        weatherRepositoryImpl: WeatherRepositoryImpl
    ): WeatherRepository
}
