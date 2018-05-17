package mcs.salazar.jesus.soliddemo.callback;

/**
 * Created by jesussalazar on 5/17/18.
 */

public interface AsynchronousCallback extends BaseCallback {
    // Interface segregation
    public void prepareThread();
}
