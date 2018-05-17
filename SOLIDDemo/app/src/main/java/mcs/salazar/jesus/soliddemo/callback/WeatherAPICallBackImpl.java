package mcs.salazar.jesus.soliddemo.callback;

/**
 * Created by jesussalazar on 5/17/18.
 */

public class WeatherAPICallBackImpl implements WeatherAPICallback {

    @Override
    public void execute() {
        System.out.println("Execute()");
    }

    // setHeaders should be implemented
    @Override
    public void setHeaders() {
        System.out.println("setHeaders()");
    }
}
