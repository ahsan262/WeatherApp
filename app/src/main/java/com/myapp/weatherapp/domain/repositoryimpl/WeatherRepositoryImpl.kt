package com.myapp.weatherapp.domain.repositoryimpl


import com.myapp.weatherapp.utils.ApiResult
import com.myapp.weatherapp.data.datasource.WeatherDataSource
import com.myapp.weatherapp.domain.repository.WeatherRepository
import com.myapp.weatherapp.data.source.base.BaseApiResponse
import com.myapp.weatherapp.data.source.response.WeatherResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn



class WeatherRepositoryImpl(private val weatherDataSource: WeatherDataSource) :
    BaseApiResponse(), WeatherRepository {

    override suspend fun fetchWeatherInfo(cityName: String,): Flow<ApiResult<WeatherResponse>> {
        return flow {
            emit(
                safeApiCall {
                    weatherDataSource.getWeatherReport(cityName)
                }
            )
        }.flowOn(Dispatchers.IO)
    }
}