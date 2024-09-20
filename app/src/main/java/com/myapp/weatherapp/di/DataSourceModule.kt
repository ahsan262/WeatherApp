package com.myapp.weatherapp.di

import com.myapp.weatherapp.data.datasource.WeatherDataSource
import com.myapp.weatherapp.data.datasourceimpl.WeatherDataSourceImpl
import com.myapp.weatherapp.data.source.remote.WeatherApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class DataSourceModule {

    @Singleton
    @Provides
    fun provideWeatherDataSource(weatherApi: WeatherApi): WeatherDataSource {
        return WeatherDataSourceImpl(weatherApi)
    }
}