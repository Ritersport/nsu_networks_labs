package ru.nsu.leorita.data.services.openweather.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class OpenWeatherResponse {
    @SerializedName("weather")
    public List<OpenWeatherInfo> weather;

    @SerializedName("main")
    public OpenWeatherMain main;

    @SerializedName("visibility")
    public Integer visibility;

    @SerializedName("wind.speed")
    public Double windSpeed;

    @SerializedName("clouds.all")
    public Double cloudiness;

}
