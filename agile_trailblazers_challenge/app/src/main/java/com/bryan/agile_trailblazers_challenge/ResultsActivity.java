package com.bryan.agile_trailblazers_challenge;

import android.content.DialogInterface;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.format.DateFormat;
import android.text.style.RelativeSizeSpan;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bryan.agile_trailblazers_challenge.model.WeatherItem;
import com.bryan.agile_trailblazers_challenge.model.WeatherResults;
import com.bryan.agile_trailblazers_challenge.remote.SOService;
import com.bryan.agile_trailblazers_challenge.util.ApiUtil;
import com.bryan.agile_trailblazers_challenge.util.ErrorHandlerUtil;
import com.bryan.agile_trailblazers_challenge.util.NetworkUtil;

import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 *   The ResultsActivity displays the weather information of given zip code.
 *   The zip code submitted by the user is used as a parameter to retrieve weather
 *      information for that location.
 *   If successful, screen will display the weather info.
 *   If unsuccessful, screen will display alert dialog box for invalid zip code or other error.
 *
 */
public class ResultsActivity extends AppCompatActivity {

    private static final String API_KEY = "0730db83cbf8e0033457b80447cee189";
    private static final String TAG = ResultsActivity.class.getName();
    private static final String UNITS = "Imperial";
    private static final int DEFAULT_FIRST_ITEM = 0;

    public static final String THE_ZIPCODE = "The_ZipCode";

    private String theZipCode;

    private SOService mService;

    @BindView(R.id.main_condition) TextView mainCondition;
    @BindView(R.id.description_condition) TextView descriptionCondition;
    @BindView(R.id.temperature) TextView temperature;
    @BindView(R.id.wind_speed) TextView windSpeed;
    @BindView(R.id.pressure) TextView pressure;
    @BindView(R.id.humidity) TextView humidity;
    @BindView(R.id.sunrise) TextView sunrise;
    @BindView(R.id.city_name) TextView cityName;
    @BindView(R.id.progressBar) ProgressBar spinner;

    @BindView(R.id.weather_details_layout) FrameLayout weatherDetailslayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);

        ButterKnife.bind(this);

        Bundle extra = getIntent().getExtras();

        theZipCode = extra.getString(THE_ZIPCODE);

        ActionBar actionBar = this.getSupportActionBar();

        if (theZipCode != null) {
            actionBar.setTitle(getString(R.string.search_title, theZipCode));
        }

        mService = ApiUtil.getSOService();

        getWeatherInfo(theZipCode);
    }

    // Retrieve weather information for given zip code.
    public void getWeatherInfo(final String zipcode) {
        spinner.setVisibility(View.VISIBLE);

        if (NetworkUtil.isOnline(getApplicationContext())) {

            mService.getResults(zipcode, UNITS, API_KEY)
                    .enqueue(new Callback<WeatherResults>() {
                        @Override
                        public void onResponse(Call<WeatherResults> call,
                                               Response<WeatherResults> response) {
                            if (response.isSuccessful()) {

                                WeatherItem weatherItem = new WeatherItem(
                                        response.body().getWeather().get(DEFAULT_FIRST_ITEM).getMain(),
                                        response.body().getWeather().get(DEFAULT_FIRST_ITEM).getDescription(),
                                        response.body().getMain().getTemp(),
                                        response.body().getWind().getSpeed(),
                                        response.body().getMain().getPressure(),
                                        response.body().getMain().getHumidity(),
                                        response.body().getSys().getSunrise(),
                                        response.body().getName() + ", " +
                                                response.body().getSys().getCountry());

                                initializeViews(weatherItem);

                                Log.d(TAG, "Posts loaded from API");
                            } else {
                                int statusCode = response.code();

                                setAlertDialog(getString(R.string.dialog_invalid_zipcode_message, theZipCode),
                                               getString(R.string.dialog_invalid_zipcode_title),
                                               getString(R.string.dialog_invalid_zipcode_goback),
                                               null);

                                Log.d(TAG, "Error - Code: " + statusCode);
                            }
                        }

                        @Override
                        public void onFailure(Call<WeatherResults> call, Throwable t) {

                            setAlertDialog(getString(R.string.dialog_error_message),
                                    getString(R.string.dialog_error_title),
                                    getString(R.string.dialog_error_goback),
                                    getString(R.string.dialog_error_tryagain));

                            Log.d(TAG, "Error loading from API");
                        }
                    });
        } else {
            setAlertDialog(getString(R.string.dialog_network_error_message),
                    getString(R.string.dialog_network_error_title),
                    getString(R.string.dialog_network_error_goback),
                    getString(R.string.dialog_network_error_tryagain));
        }
    }

    // Display alert dialog if any issue occurs during API call.
    public void setAlertDialog(String message, String title, String positive, String negative) {
        weatherDetailslayout.setVisibility(View.GONE);
        spinner.setVisibility(View.GONE);

        DialogInterface.OnClickListener positiveListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                finish();
            }
        };
        DialogInterface.OnClickListener negativeListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                getWeatherInfo(theZipCode);
            }
        };

        ErrorHandlerUtil.showErrorDialog(this, message, title, positive,
                positiveListener, negative, negativeListener);
    }

    // If successful, display weather information for given zip code.
    public void initializeViews(WeatherItem weatherItem) {
        mainCondition.setText(weatherItem.getMainCondition());
        descriptionCondition.setText(weatherItem.getDescriptionCondition());

        String temp = String.valueOf(weatherItem.getTemperature());
        String s = temp + "\u00b0F";
        SpannableString spannable = new SpannableString(s);
        spannable.setSpan(new RelativeSizeSpan(2f), 0, temp.length(), 0); // set size
        temperature.setText(spannable);

        windSpeed.setText(getString(R.string.results_wind_speed, String.valueOf(weatherItem.getWindSpeed())));

        pressure.setText(getString(R.string.results_pressure, String.valueOf(weatherItem.getPressure())));

        String theHumidity = String.valueOf(weatherItem.getHumidity()) + "%";
        humidity.setText(theHumidity);

        sunrise.setText(getString(R.string.results_sunrise, getTime(weatherItem.getSunrise())));

        cityName.setText(getString(R.string.results_city_name, String.valueOf(weatherItem.getCityName())));

        spinner.setVisibility(View.GONE);
        weatherDetailslayout.setVisibility(View.VISIBLE);
    }

    // Get time of sunrise.
    public String getTime(long time) {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(time * 1000);
        return DateFormat.format("hh:mm", cal).toString();
    }
}
