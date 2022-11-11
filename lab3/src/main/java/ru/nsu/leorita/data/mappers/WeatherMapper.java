package ru.nsu.leorita.data.mappers;

import ru.nsu.leorita.data.services.openweather.models.OpenWeatherResponse;
import ru.nsu.leorita.domain.models.Weather;

public class WeatherMapper {
    public static Weather createWeather(OpenWeatherResponse openWeatherResponse) {
        Weather weather = new Weather();

        weather.setCloudiness(openWeatherResponse.cloudiness);
        weather.setWeatherDescription(openWeatherResponse.weather.get(0).weatherDescription);
        weather.setFeelsLike(openWeatherResponse.main.feelsLike);
        weather.setTemperature(openWeatherResponse.main.temperature);
        weather.setPressure(openWeatherResponse.main.pressure);
        weather.setVisibility(openWeatherResponse.visibility);
        weather.setMainInfo(openWeatherResponse.weather.get(0).mainInfo);
        weather.setWindSpeed(openWeatherResponse.windSpeed);

        return weather;
    }
}
