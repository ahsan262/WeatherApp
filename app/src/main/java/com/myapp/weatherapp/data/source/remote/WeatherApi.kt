package com.myapp.weatherapp.data.source.remote


import com.myapp.weatherapp.BuildConfig
import com.myapp.weatherapp.data.source.response.WeatherResponse
import retrofit2.Response
import retrofit2.http.*


interface WeatherApi {

    @GET("weather")
    suspend fun getWeather(
        @Query("q") cityName: String,
        @Query("appid") apiKey: String = BuildConfig.API_KEY
    ): Response<WeatherResponse>
}