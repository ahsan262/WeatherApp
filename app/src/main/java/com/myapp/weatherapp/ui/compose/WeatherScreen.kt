package com.myapp.weatherapp.ui.compose

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import com.myapp.weatherapp.R
import com.myapp.weatherapp.data.source.response.Clouds
import com.myapp.weatherapp.data.source.response.Coord
import com.myapp.weatherapp.data.source.response.Main
import com.myapp.weatherapp.data.source.response.Rain
import com.myapp.weatherapp.data.source.response.Sys
import com.myapp.weatherapp.data.source.response.Weather
import com.myapp.weatherapp.data.source.response.WeatherResponse
import com.myapp.weatherapp.data.source.response.Wind
import com.myapp.weatherapp.ui.MainViewModel
import com.myapp.weatherapp.ui.theme.WeatherAppTheme
import com.myapp.weatherapp.utils.ApiResult
import kotlin.math.roundToInt

@Composable
fun WeatherScreen(viewModel: MainViewModel) {

    val weatherResult by viewModel.weatherLiveData.observeAsState(ApiResult.Nothing())

    WeatherAppTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier
                    .padding(top = 16.dp)
                    .statusBarsPadding()
                    .safeContentPadding()
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {


                Spacer(modifier = Modifier.height(36.dp))
                Image(
                    painter = painterResource(id = R.drawable.weather_forecast),
                    contentDescription = "",
                    modifier = Modifier
                        .size(150.dp)
                        .clip(RoundedCornerShape(20.dp))
                )

                Spacer(modifier = Modifier.height(36.dp))
                CityInput(viewModel = viewModel)

                Spacer(modifier = Modifier.height(16.dp))

                when (val result = weatherResult) {
                    is ApiResult.Loading -> {
                        CircularProgressIndicator()
                    }

                    is ApiResult.Success -> {
                        result.data?.let { WeatherDisplay(it) }
                    }

                    is ApiResult.Error -> {
                        Text(
                            text = result.message?.uppercase() ?: "An unknown error occurred",
                            color = MaterialTheme.colorScheme.error,
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }

                    else -> {
                        Text("Enter a city to get weather data.")
                    }
                }
            }
        }
    }
}

@Composable
fun CityInput(viewModel: MainViewModel) {
    var city by remember { mutableStateOf("") }

    Column {
        TextField(
            value = city,
            onValueChange = { city = it },
            label = { Text("Enter city") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            shape = RoundedCornerShape(12.dp),
            leadingIcon = {
                Icon(
                    painter = painterResource(id = R.drawable.pin),
                    contentDescription = "",
                    modifier = Modifier
                        .size(24.dp)

                )
            },
            colors = TextFieldDefaults.colors(
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            )
        )

        Spacer(modifier = Modifier.height(8.dp))

        Button(
            onClick = {
                if (city.trim().isNotEmpty()) {
                    viewModel.fetchWeather(city)
                }
            }, modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {
            Text("Get Weather")
        }
    }
}


@Composable
fun WeatherDisplay(weather: WeatherResponse) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {


        LazyVerticalGrid(columns = GridCells.Fixed(2)) {
            item {
                WeatherDisplayItem(
                    title = "Temperature",
                    body = "${(weather.main.temp - 273.15).roundToInt()} °C",
                    icon = R.drawable.weather
                )
            }

            item {
                WeatherDisplayItem(
                    title = "Feels like",
                    body = "${(weather.main.feels_like - 273.15).roundToInt()} °C",
                    icon = R.drawable.weather
                )
            }

            item {
                WeatherDisplayItem(
                    title = "City",
                    body = weather.name,
                    icon = R.drawable.pin
                )
            }

            item {
                WeatherDisplayItem(
                    title = "Description",
                    body = weather.weather[0].description.uppercase(),
                    icon = R.drawable.info
                )
            }

            item {
                WeatherDisplayItem(
                    title = "Humidity",
                    body = "${weather.main.humidity} %",
                    icon = R.drawable.humidity
                )
            }

            item {
                WeatherDisplayItem(
                    title = "Wind Speed",
                    body = "${weather.wind.speed} m/s",
                    icon = R.drawable.wind
                )
            }

        }

    }
}

@Composable
fun WeatherDisplayItem(title: String, body: String, icon: Int) {
    Row(
        modifier = Modifier
            .padding(10.dp)
            .wrapContentSize(Alignment.CenterStart),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Icon(
            painter = painterResource(id = icon),
            contentDescription = "",
            tint = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier
                .background(
                    MaterialTheme.colorScheme.surfaceVariant, shape = CircleShape
                )
                .size(44.dp)
                .padding(10.dp)
        )

        Column(modifier = Modifier.padding(start = 16.dp), verticalArrangement = Arrangement.Center) {
            Text(
                text = title,
                fontSize = TextUnit(12f, TextUnitType.Sp),
                maxLines = 1,
                color = MaterialTheme.colorScheme.surfaceTint
            )
            Text(
                text = body,
                fontSize = TextUnit(13f, TextUnitType.Sp),
                maxLines = 1,
                color = MaterialTheme.colorScheme.onBackground
            )
        }
    }
}


@Preview
@Composable
fun WeatherDisplayPreviewTest() {
    val weatherResponse = WeatherResponse(
        coord = Coord(
            lon = 139.6917, lat = 35.6895
        ), weather = listOf(
            Weather(
                id = 801, main = "Clouds", description = "few clouds", icon = "02d"
            )
        ), main = Main(
            temp = 22.5f,
            feels_like = 24.0f,
            temp_min = 20.0f,
            temp_max = 25.0f,
            pressure = 1012,
            humidity = 60,
            sea_level = 1015,
            grnd_level = 1009
        ), wind = Wind(
            speed = 5.1f, deg = 180, gust = 7.0f
        ), clouds = Clouds(
            all = 20
        ), rain = Rain(
            oneHour = 0.5f
        ), name = "Tokyo", sys = Sys(
            country = "JP", sunrise = 1632950400,
            sunset = 1632993600
        ), dt = 1632979200
    )

    WeatherDisplay(weather = weatherResponse)
}
