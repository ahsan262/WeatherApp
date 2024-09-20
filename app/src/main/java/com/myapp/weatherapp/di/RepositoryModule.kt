package com.myapp.weatherapp.di


import com.myapp.weatherapp.data.datasource.WeatherDataSource
import com.myapp.weatherapp.domain.repository.WeatherRepository
import com.myapp.weatherapp.domain.repositoryimpl.WeatherRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {

    @Singleton
    @Provides
    fun providesWeatherRepository(weatherDataSource: WeatherDataSource): WeatherRepository {
        return WeatherRepositoryImpl(weatherDataSource)
    }
}