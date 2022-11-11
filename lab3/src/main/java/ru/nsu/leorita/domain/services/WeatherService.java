package ru.nsu.leorita.domain.services;

import io.reactivex.rxjava3.core.Single;
import ru.nsu.leorita.domain.models.Weather;

public interface WeatherService {
    Single<Weather> getWeather(double lat, double lon);
}
