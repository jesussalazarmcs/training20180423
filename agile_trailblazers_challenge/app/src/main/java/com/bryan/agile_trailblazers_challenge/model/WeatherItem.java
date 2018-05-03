package com.bryan.agile_trailblazers_challenge.model;

public class WeatherItem {

    private String mainCondition;
    private String descriptionCondition;
    private Double temperature;
    private Double windSpeed;
    private int pressure;
    private int humidity;
    private int sunrise;
    private String cityName;

    public WeatherItem (String mainCondition, String descriptionCondition, Double temperature,
                        Double windSpeed, int pressure, int humidity, int sunrise,
                        String cityName) {
        this.mainCondition = mainCondition;
        this.descriptionCondition = descriptionCondition;
        this.temperature = temperature;
        this.windSpeed = windSpeed;
        this.pressure = pressure;
        this.humidity = humidity;
        this.sunrise = sunrise;
        this.cityName = cityName;
    }

    public String getMainCondition() {
        return mainCondition;
    }

    public String getDescriptionCondition() {
        return descriptionCondition;
    }

    public Double getTemperature() {
        return temperature;
    }

    public Double getWindSpeed() {
        return windSpeed;
    }

    public int getPressure() {
        return pressure;
    }

    public int getHumidity() {
        return humidity;
    }

    public int getSunrise() {
        return sunrise;
    }

    public String getCityName() {
        return cityName;
    }
}
