package com.myapp.weatherapp.domain.usecase

import com.myapp.weatherapp.domain.repository.WeatherRepository


class WeatherUseCase(private val weatherRepository: WeatherRepository) {

    suspend fun callWeatherApi(cityName: String) = weatherRepository.fetchWeatherInfo(cityName)
}