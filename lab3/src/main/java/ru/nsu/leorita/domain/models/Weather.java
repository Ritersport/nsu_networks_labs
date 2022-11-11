package ru.nsu.leorita.domain.models;

public class Weather {
    private Integer visibility;
    private Double windSpeed;
    private Double cloudiness;
    private String mainInfo;
    private String weatherDescription;
    private Double temperature;
    private Double feelsLike;
    private Double pressure;

    public Integer getVisibility() {
        return visibility;
    }

    public void setVisibility(Integer visibility) {
        this.visibility = visibility;
    }

    public Double getWindSpeed() {
        return windSpeed;
    }

    public void setWindSpeed(Double windSpeed) {
        this.windSpeed = windSpeed;
    }

    public Double getCloudiness() {
        return cloudiness;
    }

    public void setCloudiness(Double cloudiness) {
        this.cloudiness = cloudiness;
    }

    public String getMainInfo() {
        return mainInfo;
    }

    public void setMainInfo(String mainInfo) {
        this.mainInfo = mainInfo;
    }

    public String getWeatherDescription() {
        return weatherDescription;
    }

    public void setWeatherDescription(String weatherDescription) {
        this.weatherDescription = weatherDescription;
    }

    public Double getTemperature() {
        return temperature;
    }

    public void setTemperature(Double temperature) {
        this.temperature = temperature;
    }

    public Double getFeelsLike() {
        return feelsLike;
    }

    public void setFeelsLike(Double feelsLike) {
        this.feelsLike = feelsLike;
    }

    public Double getPressure() {
        return pressure;
    }

    public void setPressure(Double pressure) {
        this.pressure = pressure;
    }

    public String toString() {
        String result = "";
        if (weatherDescription != null) {
            result += "Weather: " + this.weatherDescription + "\n";
        }
        if (temperature != null) {
            result += "Temperature: " + this.temperature + "\n";
        }
        if (feelsLike != null) {
            result += "Feels like: " + this.feelsLike + "\n";
        }
        if (cloudiness != null) {
            result += "Cloudiness: " + this.cloudiness + "%\n";
        }
        if (visibility != null) {
            result += "Visibility: " + this.visibility + " meters\n";
        }
        return result;
    }
}
