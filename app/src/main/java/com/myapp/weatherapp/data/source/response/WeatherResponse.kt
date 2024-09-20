package com.myapp.weatherapp.data.source.response

import com.google.gson.annotations.SerializedName

data class WeatherResponse(
    val coord: Coord,
    val weather: List<Weather>,
    val main: Main,
    val wind: Wind,
    val clouds: Clouds,
    val rain: Rain?,
    val name: String,
    val sys: Sys,
    val dt: Long
)

data class Coord(
    val lon: Double,
    val lat: Double
)

data class Weather(
    val id: Int,
    val main: String,
    val description: String,
    val icon: String
)

data class Main(
    val temp: Float,
    val feels_like: Float,
    val temp_min: Float,
    val temp_max: Float,
    val pressure: Int,
    val humidity: Int,
    val sea_level: Int,
    val grnd_level: Int
)

data class Wind(
    val speed: Float,
    val deg: Int,
    val gust: Float
)

data class Clouds(
    val all: Int
)

data class Rain(
    @SerializedName("1h")
    val oneHour: Float
)

data class Sys(
    val country: String,
    val sunrise: Long,
    val sunset: Long
)
