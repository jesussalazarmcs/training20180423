package com.example.challenge.servicesample;

import android.app.Service;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;


public class DownloaderService extends Service {

    public static final String EXTRA_IMAGE_ID = "KEY_IMAGE";
    private final IBinder mBinder = new Binder();

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return Service.START_NOT_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        String url = intent.getStringExtra(EXTRA_IMAGE_ID);
        downloadInformation(url);
        return mBinder;
    }

    public Bitmap getInformation(String url) {
        return downloadInformation(url);
    }

    private Bitmap downloadInformation(String stringUrl){
        // TODO: Run the image retrieval on a new Thread:
       return downloadImage(decodeURL(stringUrl));
    }

    public class Binder extends android.os.Binder {
        DownloaderService getService() {
            return DownloaderService.this;
        }
    }

    private URL decodeURL(String stringUrl) {
        URL resourceUrl = null;
        try {
            resourceUrl = new URL(stringUrl);
        } catch (MalformedURLException e) {
            Log.d(this.getClass().getSimpleName(), "Could not decode url");
        }
        return resourceUrl;
    }

    private Bitmap downloadImage(URL resourceUrl){
        HttpURLConnection urlConnection = null;
        Bitmap retrievedImage = null;
        try {
            urlConnection = (HttpURLConnection) resourceUrl.openConnection();
            urlConnection.getRequestMethod();
            urlConnection.connect();
            int responseCode = urlConnection.getResponseCode();
            if (responseCode == 200) {
                retrievedImage = BitmapFactory.decodeStream(urlConnection.getInputStream());
            }
        } catch (Exception e) {
            // Handle exception during image retrieval
            retrievedImage = null;
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
        }
        return retrievedImage;
    }


}
