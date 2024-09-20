package com.myapp.weatherapp.di

import com.myapp.weatherapp.domain.repository.WeatherRepository
import com.myapp.weatherapp.domain.usecase.WeatherUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class UseCaseModule {

    @Singleton
    @Provides
    fun provideWeatherUseCase(weatherRepository: WeatherRepository): WeatherUseCase {
        return WeatherUseCase(weatherRepository)
    }
}