package com.myapp.weatherapp.data.datasource

import com.myapp.weatherapp.data.source.response.WeatherResponse
import retrofit2.Response


interface WeatherDataSource {
    suspend fun getWeatherReport(cityName: String): Response<WeatherResponse>
}