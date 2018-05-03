package com.bryan.agile_trailblazers_challenge;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import com.bryan.agile_trailblazers_challenge.util.NetworkUtil;
import com.bryan.agile_trailblazers_challenge.util.ZipCodeUtil;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.bryan.agile_trailblazers_challenge.ResultsActivity.THE_ZIPCODE;

/**
 *   The SearchActivity allows the user to input and submit a zip code for weather information.
 *   The user is only allowed to use numbers and the max length is 5.
 *   Once the user has entered 5 numbers for the zip code, the SUBMIT button is enabled to submit
 *     for weather information, which will be provided on the next screen assuming the zip code is
 *     valid and that there are no network issues.
 *
 */
public class SearchActivity extends AppCompatActivity {

    @BindView(R.id.zipcode) EditText zipCode;
    @BindView(R.id.btn_submit) Button submitButton;

    private Context context = this;

    private Snackbar mySnackbar;

    private boolean snackBarIsShow = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        ButterKnife.bind(this);

        setListeners();

        checkConnection();
    }

    // Check connection before submitting zip code.
    public void checkConnection() {
        if (NetworkUtil.isOnline(getApplicationContext())) {
            if (zipCode.getText().toString().length() > 4) {
                submitButton.setEnabled(true);
            }
        }
        else {
            mySnackbar = Snackbar.make(findViewById(R.id.search_layout),
                    R.string.sbar_noconnect, Snackbar.LENGTH_INDEFINITE)
                    .setAction(R.string.sbar_tryagain, new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            checkConnection();
                        }
                    });

            mySnackbar.show();

            snackBarIsShow = true;
        }
    }

    // zipCode.addTextChangedListener -- Verify if user has entered 5 numbers for zip code.
    // submitButton.setOnClickListener -- When enabled, submit zip code to the next screen.
    public void setListeners() {

        zipCode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (ZipCodeUtil.isZipCode(zipCode.getText().toString()) &&
                        NetworkUtil.isOnline(getApplicationContext())) {
                    submitButton.setEnabled(true);
                    if (snackBarIsShow) {
                        mySnackbar.dismiss();

                        snackBarIsShow = false;
                    }
                }
                else {
                    if (ZipCodeUtil.isZipCode(zipCode.getText().toString()) && !snackBarIsShow) {
                        checkConnection();
                    }

                    submitButton.setEnabled(false);
                }
            }
        });

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkConnection();

                if (NetworkUtil.isOnline(getApplicationContext())) {
                    InputMethodManager inputManager =
                            (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                    inputManager.hideSoftInputFromWindow((null == getCurrentFocus())
                                    ? null : getCurrentFocus().getWindowToken(),
                            InputMethodManager.HIDE_NOT_ALWAYS);

                    Intent intent = new Intent(context, ResultsActivity.class);

                    intent.putExtra(THE_ZIPCODE, zipCode.getText().toString());

                    startActivity(intent);
                }
                else {
                    submitButton.setEnabled(false);
                }
            }
        });
    }
}
