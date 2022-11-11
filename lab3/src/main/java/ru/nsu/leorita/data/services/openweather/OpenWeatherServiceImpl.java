package ru.nsu.leorita.data.services.openweather;

import io.reactivex.rxjava3.core.Single;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import ru.nsu.leorita.Config;
import ru.nsu.leorita.data.mappers.WeatherMapper;
import ru.nsu.leorita.domain.models.Weather;
import ru.nsu.leorita.domain.services.WeatherService;

public class OpenWeatherServiceImpl implements WeatherService {
    private Retrofit retrofit;
    private OpenWeatherWebAPI openWeatherWebAPI;
    private String BASE_URL = Config.OPEN_WEATHER_BASE_URL;
    private String API_KEY = Config.OPEN_WEATHER_API_KEY;
    private String MEASUREMENT_UNITS = "metric";

    public OpenWeatherServiceImpl() {
        this.retrofit = new Retrofit.Builder()
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BASE_URL)
                .build();
        this.openWeatherWebAPI = retrofit.create(OpenWeatherWebAPI.class);
    }

    @Override
    public Single<Weather> getWeather(double lat, double lon) {
        return openWeatherWebAPI.getWeather(lat, lon, API_KEY, MEASUREMENT_UNITS).map(WeatherMapper::createWeather);
    }
}
