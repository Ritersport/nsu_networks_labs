package ru.nsu.leorita.data.services.openweather.models;

import com.google.gson.annotations.SerializedName;

public class OpenWeatherInfo {
    @SerializedName("main")
    public String mainInfo;

    @SerializedName("description")
    public String weatherDescription;
}
