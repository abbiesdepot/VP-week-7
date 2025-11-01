package com.abbie.week78.data.repositories

import com.abbie.week78.data.services.WeatherService
import com.abbie.week78.ui.model.WeatherModel

class WeatherRepository (private val service: WeatherService) {
    suspend fun Getweathernyacity(city: String): WeatherModel{
        val weathers = service.Getweathernyacity(
            namecity = city,
            units = "metric",
            apiKey = "9aedd10161b79db9eee645517f080da6"
        ).body()!!
        return WeatherModel(
            cityName = weathers.name,
            dateTime = weathers.dt,
            updatedTime = weathers.dt.toString(),
            icon = weathers.weather[0].icon,
            temperature = weathers.main.temp,
            weatherCondition = weathers.weather[0].main,
            humidity = weathers.main.humidity,
            windSpeed = weathers.wind.speed,
            feelsLike = weathers.main.feels_like,
            rainFall = weathers.rain?.`1h` ?: 0.0,
            pressure = weathers.main.pressure,
            cloud = weathers.clouds.all,
            sunriseTime = weathers.sys.sunrise,
            sunsetTime = weathers.sys.sunset
        )
    }
    fun getWeatherIconUrl(iconId: String): String {
        val url ="https://openweathermap.org/img/wn/$iconId@2x.png"
        return url
    }
}