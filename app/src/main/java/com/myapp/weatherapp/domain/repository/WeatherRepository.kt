package com.myapp.weatherapp.domain.repository

import com.myapp.weatherapp.data.source.response.WeatherResponse
import com.myapp.weatherapp.utils.ApiResult
import kotlinx.coroutines.flow.Flow


interface WeatherRepository {
    suspend fun fetchWeatherInfo(cityName: String): Flow<ApiResult<WeatherResponse>>
}