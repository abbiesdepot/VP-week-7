package com.abbie.week78.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.abbie.week78.data.repositories.WeatherRepository
import com.abbie.week78.ui.model.WeatherModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class WeatherViewModel(private val repository: WeatherRepository) : ViewModel() {

    private val _weatherState = MutableStateFlow<Weatherstate>(Weatherstate.Initial)
    val weatherState: StateFlow<Weatherstate> = _weatherState

    fun searchWeather(city: String) {
        viewModelScope.launch {
            _weatherState.value = Weatherstate.Loading
            try {
                val weather = repository.Getweathernyacity(city)
                _weatherState.value = Weatherstate.Success(weather)
            } catch (e: Exception) {
                _weatherState.value = Weatherstate.Error(e.message ?: "Unknown error")
            }
        }
    }
    
    fun getWeatherIconUrl(iconId: String): String {
        return repository.getWeatherIconUrl(iconId)
    }

}


// UI State
sealed class Weatherstate {
    object Initial : Weatherstate()
    object Loading : Weatherstate()
    data class Success(val weather: WeatherModel) : Weatherstate()
    data class Error(val message: String) : Weatherstate()
}
