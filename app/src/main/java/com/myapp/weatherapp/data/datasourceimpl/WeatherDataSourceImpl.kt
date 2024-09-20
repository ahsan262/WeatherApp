package com.myapp.weatherapp.data.datasourceimpl

import com.myapp.weatherapp.data.source.remote.WeatherApi
import com.myapp.weatherapp.data.datasource.WeatherDataSource
import com.myapp.weatherapp.data.source.response.WeatherResponse
import retrofit2.Response


class WeatherDataSourceImpl(private var api: WeatherApi) : WeatherDataSource {

    override suspend fun getWeatherReport(cityName: String,): Response<WeatherResponse> {
        return api.getWeather(cityName)
    }

}