package com.abbie.week78.ui.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Air
import androidx.compose.material.icons.filled.ArrowDownward
import androidx.compose.material.icons.filled.ArrowUpward
import androidx.compose.material.icons.filled.Cloud
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Thermostat
import androidx.compose.material.icons.filled.Umbrella
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material.icons.filled.WaterDrop
import androidx.compose.material.icons.filled.WbSunny
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import coil.compose.AsyncImage
import com.abbie.week78.R
import com.abbie.week78.data.repositories.WeatherRepository
import com.abbie.week78.data.services.WeatherService
import com.abbie.week78.ui.viewmodel.WeatherViewModel
import com.abbie.week78.ui.viewmodel.Weatherstate
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun PanPanWeather() {
    val weatherService = remember { WeatherService.create() }
    val repository = remember { WeatherRepository(weatherService) }
    val viewModel = remember { WeatherViewModel(repository) }

    var inputCity by remember { mutableStateOf("") }

//    val Weatherstate by remember { mutableStateOf(Weatherstate.Success) }
    val weatherState by viewModel.weatherState.collectAsState()



    Box(
        modifier = Modifier.fillMaxSize()
    ) {

        Image(
            painter = painterResource(id = R.drawable.backgroundweather),
            contentDescription = null,
            modifier = Modifier
                .fillMaxSize()
//                .fillMaxHeight()
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 20.dp)
        ) {

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                OutlinedTextField(
                    value = inputCity,
                    onValueChange = { inputCity = it },
                    placeholder = {
                        Text(
                            "Enter city name...",
                            color = Color.White.copy(alpha = 0.6f)
                        )
                    },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = "Search",
                            tint = Color.White.copy(alpha = 0.7f),
                            modifier = Modifier.size(20.dp)
                        )
                    },
                    modifier = Modifier
                        .weight(1f)
                        .width(60.dp),
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
                    keyboardActions = KeyboardActions(onSearch = {
                        if (inputCity.isNotBlank()) {
                            viewModel.searchWeather(inputCity)
                        }
                    }),
                    shape = RoundedCornerShape(28.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = Color.White.copy(alpha = 0.15f),
                        unfocusedContainerColor = Color.White.copy(alpha = 0.1f),
                        focusedTextColor = Color.White,
                        unfocusedTextColor = Color.White,
                        focusedBorderColor = Color.White.copy(alpha = 0.3f),
                        unfocusedBorderColor = Color.White.copy(alpha = 0.2f)
                    )
                )

                Spacer(modifier = Modifier.width(10.dp))

                Button(
                    onClick = {
                        if (inputCity.isNotBlank()) {
                            viewModel.searchWeather(inputCity)
                        }
                    },
                    shape = RoundedCornerShape(28.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.White.copy(alpha = 0.2f)
                    ),
                    contentPadding = PaddingValues(horizontal = 10.dp, vertical = 10.dp),
                    modifier = Modifier.width(100.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "Search",
                        tint = Color.White,
                        modifier = Modifier.size(14.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Search", color = Color.White, fontSize = 14.sp)
                }
            }

            Spacer(modifier = Modifier.height(30.dp))


            when (val state = weatherState) {
                is Weatherstate.Initial -> {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(horizontal = 20.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        item {
                            Icon(
                                imageVector = Icons.Default.Search,
                                contentDescription = "Search",
                                modifier = Modifier.size(90.dp),
                                tint = Color.LightGray
                            )

                            Spacer(modifier = Modifier.height(16.dp))

                            Text(
                                text = "Search for a city to get started",
                                color = Color.LightGray,
                                fontSize = 16.sp,
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                }
                is Weatherstate.Error -> {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(horizontal = 20.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        item {
                            Icon(
                                imageVector = Icons.Default.Warning,
                                contentDescription = "ERROR",
                                modifier = Modifier.size(90.dp),
                                tint = Color.Red
                            )

                            Spacer(modifier = Modifier.height(16.dp))

                            Text(
                                text = "Oops! Something went wrong.",
                                color = Color.White,
                                fontSize = 16.sp,
                                textAlign = TextAlign.Center
                            )

                            Spacer(modifier = Modifier.height(10.dp))


                            Text(
                                text = "HTTP 404 Not Found",
                                color = Color.LightGray,
                                fontSize = 13.sp,
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                }

                is Weatherstate.Success -> {
                    weatherdisplayed(
                        weather = state.weather,
                        iconUrl = viewModel.getWeatherIconUrl(state.weather.icon)
                    )
                }

                is Weatherstate.Loading -> {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        item {
                            CircularProgressIndicator(color = Color.White)
                            Spacer(modifier = Modifier.height(16.dp))
                            Text("Loading...", color = Color.White, fontSize = 16.sp)
                        }
                    }
                }

            }
        }
    }
}


@Composable
fun weatherdisplayed(
    weather: com.abbie.week78.ui.model.WeatherModel,
    iconUrl: String

) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Date & City
        item {
            Text(
                text = SimpleDateFormat("MMMM dd", Locale.getDefault())
                    .format(Date(weather.dateTime.toLong() * 1000L)),
                color = Color.White,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "Updated as of ${SimpleDateFormat("HH:mm a", Locale.getDefault())
                    .format(Date(weather.dateTime.toLong() * 1000))}",
                color = Color.White.copy(alpha = 0.7f),
                fontSize = 14.sp
            )
            Spacer(modifier = Modifier.height(40.dp))
        }

        // Weather Icon from API
        item {
            AsyncImage(
                model = iconUrl,
                contentDescription = "Weather Icon",
                modifier = Modifier.size(100.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))
        }

        // Temperature & City
        item {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = weather.weatherCondition,
                    color = Color.White,
                    fontSize = 18.sp
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "${weather.temperature.toInt()}°C",
                    color = Color.White,
                    fontSize = 64.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = weather.cityName,
                    color = Color.White.copy(alpha = 0.8f),
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Medium
                )
            }
            Spacer(modifier = Modifier.height(40.dp))
        }

        // Weather Stats Grid
        item {
            Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                // Row 1
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    WeatherStatsCard(
                        icon = Icons.Default.WaterDrop,
                        label = "Humidity",
                        value = "${weather.humidity}%"
                    )
                    WeatherStatsCard(
                        icon = Icons.Default.Air,
                        label = "Wind",
                        value = "${weather.windSpeed} km/h"
                    )
                    WeatherStatsCard(
                        icon = Icons.Default.Thermostat,
                        label = "Feels Like",
                        value = "${weather.feelsLike.toInt()}°"
                    )
                }

                // Row 2
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    WeatherStatsCard(
                        icon = Icons.Default.Umbrella,
                        label = "Rain Fall",
                        value = "${weather.rainFall} mm"
                    )
                    WeatherStatsCard(
                        icon = Icons.Default.WbSunny,
                        label = "Pressure",
                        value = "${weather.pressure} hPa"
                    )
                    WeatherStatsCard(
                        icon = Icons.Default.Cloud,
                        label = "Clouds",
                        value = "${weather.cloud}%"
                    )
                }
            }
            Spacer(modifier = Modifier.height(24.dp))
        }

        // Sunrise & Sunset
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                SunriseSunsetCard(
                    icon = Icons.Default.ArrowUpward,
                    label = "Sunrise",
                    value = SimpleDateFormat("h:mm a", Locale.getDefault())
                        .format(Date(weather.sunriseTime.toLong() * 1000))
                )
                SunriseSunsetCard(
                    icon = Icons.Default.ArrowDownward,
                    label = "Sunset",
                    value = SimpleDateFormat("h:mm a", Locale.getDefault())
                        .format(Date(weather.sunsetTime.toLong() * 1000))
                )
            }
            Spacer(modifier = Modifier.height(30.dp))
        }
    }
}


@Preview(showBackground = true)
@Composable
fun PanPanWeatherPreview() {
    PanPanWeather()
}
