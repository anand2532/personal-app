package com.personal.app

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

data class WeatherData(
    val location: String,
    val temperature: Double,
    val feelsLike: Double,
    val description: String,
    val humidity: Int,
    val windSpeed: Double
)

class MainViewModel : ViewModel() {

    private val _weatherData = MutableLiveData<WeatherData?>()
    val weatherData: LiveData<WeatherData?> = _weatherData

    private val _errorMessage = MutableLiveData<String?>()
    val errorMessage: LiveData<String?> = _errorMessage

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val weatherService = WeatherService.create()

    fun fetchWeather(latitude: Double, longitude: Double) {
        viewModelScope.launch {
            _isLoading.value = true
            _errorMessage.value = null

            try {
                val response = weatherService.getCurrentWeather(latitude, longitude)
                if (response.isSuccessful && response.body() != null) {
                    val weatherResponse = response.body()!!
                    val weatherData = WeatherData(
                        location = "${weatherResponse.name}, ${weatherResponse.sys.country}",
                        temperature = weatherResponse.main.temp,
                        feelsLike = weatherResponse.main.feelsLike,
                        description = weatherResponse.weather.firstOrNull()?.description?.capitalizeWords() ?: "N/A",
                        humidity = weatherResponse.main.humidity,
                        windSpeed = weatherResponse.wind.speed
                    )
                    _weatherData.value = weatherData
                } else {
                    _errorMessage.value = "Error: ${response.message()}"
                }
            } catch (e: Exception) {
                _errorMessage.value = "Error loading weather: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }

    private fun String.capitalizeWords(): String {
        return split(" ").joinToString(" ") { word ->
            word.lowercase().replaceFirstChar { char ->
                if (char.isLowerCase()) char.titlecase() else char.toString()
            }
        }
    }
}

