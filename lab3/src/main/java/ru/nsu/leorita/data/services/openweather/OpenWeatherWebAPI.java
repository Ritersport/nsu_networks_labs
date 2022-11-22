package ru.nsu.leorita.data.services.openweather;

import io.reactivex.rxjava3.core.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;
import ru.nsu.leorita.data.services.openweather.models.OpenWeatherResponse;

public interface OpenWeatherWebAPI {
    @GET("weather")
    Single<OpenWeatherResponse> getWeather(
            @Query("lat") double latitude,
            @Query("lon") double longitude,
            @Query("appid") String apiKey,
            @Query("units") String measurementUnits
    );
}
