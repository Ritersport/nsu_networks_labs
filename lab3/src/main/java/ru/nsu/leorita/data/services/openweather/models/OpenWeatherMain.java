package ru.nsu.leorita.data.services.openweather.models;

import com.google.gson.annotations.SerializedName;

public class OpenWeatherMain {
    @SerializedName("temp")
    public Double temperature;

    @SerializedName("feels_like")
    public Double feelsLike;

    @SerializedName("pressure")
    public Double pressure;
}
