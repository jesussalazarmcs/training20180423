package mcs.salazar.jesus.soliddemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import mcs.salazar.jesus.soliddemo.callback.AsynchronousCallbackImpl;
import mcs.salazar.jesus.soliddemo.callback.BaseCallback;
import mcs.salazar.jesus.soliddemo.callback.DummyCallbackImpl;
import mcs.salazar.jesus.soliddemo.callback.WeatherAPICallBackImpl;
import mcs.salazar.jesus.soliddemo.client.CallbackClient;
import mcs.salazar.jesus.soliddemo.client.CallbackClientImpl;
import mcs.salazar.jesus.soliddemo.client.DummyClientImpl;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void selectFromMenu(int option) {

        // Required, but open to new features
        CallbackClient client;
        BaseCallback callback;

        // Here is were the magic happens
        switch(option) {
            case 1:
                callback = new AsynchronousCallbackImpl();
                client = new CallbackClientImpl();
                break;
            case 2:
                callback = new WeatherAPICallBackImpl();
                client = new CallbackClientImpl();
                break;
            case 3:
                callback = new DummyCallbackImpl();
                client = new DummyClientImpl();
                break;
            default:
                return;
        }

        // This is magic
        client.doSomething(callback);

    }

}
