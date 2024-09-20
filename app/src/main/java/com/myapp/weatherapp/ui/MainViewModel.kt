package com.myapp.weatherapp.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.myapp.weatherapp.data.source.response.WeatherResponse
import com.myapp.weatherapp.domain.usecase.WeatherUseCase
import com.myapp.weatherapp.utils.ApiResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val weatherUseCase: WeatherUseCase
) : ViewModel() {

    private val responseWeather: MutableLiveData<ApiResult<WeatherResponse>> = MutableLiveData()
    val weatherLiveData: LiveData<ApiResult<WeatherResponse>> = responseWeather

    fun fetchWeather(cityName: String) {
        viewModelScope.launch {
            responseWeather.value = ApiResult.Loading()
            weatherUseCase.callWeatherApi(cityName)
                .collect { values ->
                    responseWeather.value = values
                }
        }
    }

}