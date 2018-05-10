package com.example.challenge.servicesample;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.IBinder;
import android.os.StrictMode;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import java.util.List;

public class MainActivity extends AppCompatActivity implements ServiceConnection {

    private DownloaderService downloaderService;
    private SwipeRefreshLayout swipeRefreshLayout;
    private ImageView mainImageView;
    private String sourceURL = "http://lorempixel.com/640/480/sports/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        // We will extend later on the example to support multi threading
        StrictMode.ThreadPolicy policy = new StrictMode.
                ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.main_refresh_layout);
        mainImageView = (ImageView) findViewById(R.id.main_image_view);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshData();
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        Intent intent = new Intent(this, DownloaderService.class);
        intent.putExtra(DownloaderService.EXTRA_IMAGE_ID, sourceURL);
        bindService(intent, this, Context.BIND_AUTO_CREATE);
    }

    @Override
    protected void onPause() {
        super.onPause();
        unbindService(this);
    }

    @Override
    public void onServiceConnected(ComponentName name, IBinder binder) {
        DownloaderService.Binder downloaderServiceBinder = (DownloaderService.Binder) binder;
        downloaderService = downloaderServiceBinder.getService();
        Log.d(this.getClass().getSimpleName(), "Connected to the service.");
        refreshData();
    }

    @Override
    public void onServiceDisconnected(ComponentName name) {
        downloaderService = null;
        Log.d(this.getClass().getSimpleName(), "Disconnected from the service.");
    }

    private void refreshData(){
        Bitmap bitmap = downloaderService.getInformation(sourceURL);
        if(bitmap != null) {
            BitmapDrawable bitmapDrawable = new BitmapDrawable(getResources(), bitmap);
            mainImageView.setBackground(bitmapDrawable);
        } else {
            Log.d(this.getClass().getSimpleName(), "Could not retrieve bitmap from the service");
        }
    }
}
