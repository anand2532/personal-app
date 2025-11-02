package com.personal.app

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import java.util.concurrent.TimeUnit

interface WeatherApi {
    @GET("weather")
    suspend fun getCurrentWeather(
        @Query("lat") latitude: Double,
        @Query("lon") longitude: Double,
        @Query("appid") apiKey: String = BuildConfig.WEATHER_API_KEY,
        @Query("units") units: String = "metric"
    ): retrofit2.Response<WeatherResponse>
}

data class WeatherResponse(
    val name: String,
    val sys: Sys,
    val main: Main,
    val weather: List<Weather>,
    val wind: Wind
)

data class Sys(
    val country: String
)

data class Main(
    val temp: Double,
    val feelsLike: Double,
    val humidity: Int
)

data class Weather(
    val description: String
)

data class Wind(
    val speed: Double
)

object WeatherService {
    private const val BASE_URL = "https://api.openweathermap.org/data/2.5/"

    private val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .connectTimeout(30, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .build()

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val api: WeatherApi = retrofit.create(WeatherApi::class.java)

    fun create(): WeatherApi = api
}

