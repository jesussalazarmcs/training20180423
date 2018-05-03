package com.bryan.agile_trailblazers_challenge.remote;

import com.bryan.agile_trailblazers_challenge.model.WeatherResults;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface SOService {

    @GET("/data/2.5/weather")
    Call<WeatherResults> getResults(@Query("zip") String zip,
                                    @Query("units") String units,
                                    @Query("appid") String appid);
}
