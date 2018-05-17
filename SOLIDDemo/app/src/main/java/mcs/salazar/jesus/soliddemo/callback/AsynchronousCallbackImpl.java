package mcs.salazar.jesus.soliddemo.callback;

/**
 * Created by jesussalazar on 5/17/18.
 */

public class AsynchronousCallbackImpl implements AsynchronousCallback {

    @Override
    public void execute() {
        System.out.println("Execute()");
    }

    // setHeaders is not required, instead, prepareThread... That is segregation
    @Override
    public void prepareThread() {
        System.out.println("prepareThread()");
    }
}
