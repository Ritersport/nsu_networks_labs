package ru.nsu.leorita.data.services.openweather.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class OpenWeatherResponse {
    public static class OpenWeatherWind {
        @SerializedName("speed")
        public Double windSpeed;
    }
    public static class OpenWeatherClouds {
        @SerializedName("all")
        public Double cloudiness;
    }
    @SerializedName("weather")
    public List<OpenWeatherInfo> weather;

    @SerializedName("main")
    public OpenWeatherMain main;

    @SerializedName("visibility")
    public Integer visibility;

    @SerializedName("wind")
    public OpenWeatherWind wind;

    @SerializedName("clouds")
    public OpenWeatherClouds clouds;

}
