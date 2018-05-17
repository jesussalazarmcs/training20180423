package mcs.salazar.jesus.soliddemo.client;

import mcs.salazar.jesus.soliddemo.callback.BaseCallback;

/**
 * Created by jesussalazar on 5/17/18.
 */

public class CallbackClientImpl implements CallbackClient {

    @Override
    public void doSomething(BaseCallback callback) {
        // Liskov, you do not care if callback is an asyncrhonous, or
        // WeatherAPI callback, sooooooooo   THEY ARE INTERCHANGEABLES.
        callback.execute();
    }
}
