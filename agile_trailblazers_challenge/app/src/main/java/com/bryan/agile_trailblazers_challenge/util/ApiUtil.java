package com.bryan.agile_trailblazers_challenge.util;

import com.bryan.agile_trailblazers_challenge.remote.Client;
import com.bryan.agile_trailblazers_challenge.remote.SOService;

public class ApiUtil {

    public static final String BASE_URL = "http://api.openweathermap.org/";

    public static SOService getSOService() {
        return Client.getClient(BASE_URL).create(SOService.class);
    }
}
