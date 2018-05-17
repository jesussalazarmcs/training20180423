package mcs.salazar.jesus.soliddemo.client;

import mcs.salazar.jesus.soliddemo.callback.AsynchronousCallback;
import mcs.salazar.jesus.soliddemo.callback.AsynchronousCallbackImpl;
import mcs.salazar.jesus.soliddemo.callback.BaseCallback;
import mcs.salazar.jesus.soliddemo.callback.WeatherAPICallBackImpl;
import mcs.salazar.jesus.soliddemo.callback.WeatherAPICallback;

/**
 * Created by jesussalazar on 5/17/18.
 */

public interface CallbackClient {

    // Dependency Inversion.... this should not happen
    // public void doSomething(AsynchronousCallbackImpl callback);
    // public void doSomething(WeatherAPICallBackImpl callback);

    // Dependency Inversion... you can do this
    // public void doSomething(AsynchronousCallback callback);
    // public void doSomething(WeatherAPICallback callback);

    // Or better, if you do not need to make this separation
    public void doSomething(BaseCallback callback);

}
