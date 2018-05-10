package mcs.salazar.jesus.servicesdemo;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;

/**
 * Created by jesussalazar on 1/28/18.
 */

public class MyService extends Service {

    // This is for Started Services.
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // I will use this boolean whenever I want to run in the same thread this service was invoked.
        boolean runInSameThread = intent.getExtras().getBoolean(ServiceClient.THREAD_KEY);
        System.out.println("TOKEN MAGICO - THE SERVICE IS RUNNING ONSTARTCOMMAND");
        Log.d("TOKEN MAGICO","THE SERVICE IS RUNNING ONSTARTCOMMAND");
        if(runInSameThread){
            // Oh, yes... we are actually doing this...
            System.out.println("TOKEN MAGICO - FROM THE SAME THREAD");
            Log.d("TOKEN MAGICO","FROM THE SAME THREAD");
            idle();
        } else {
            // This is not a good practice, please keep a reference to your task and check if it is running instead
            // of using anonymous.
            new Thread() {
                public void run() {
                    System.out.println("TOKEN MAGICO - FROM OTHER THREAD");
                    Log.d("TOKEN MAGICO","FROM OTHER THREAD");
                    idle();
                }
            }.start();
        }
        // Sticky or not?
        return ServiceClient.startSticky ? Service.START_STICKY : Service.START_NOT_STICKY;
    }

    private static void idle() {
        // Looool, yes... we are going to do this...
        try {
            // Simulate hard task
            System.out.println("TOKEN MAGICO - IDLING");
            Log.d("TOKEN MAGICO","IDLING");
            Thread.sleep(10000);
            System.out.println("TOKEN MAGICO - FINISH IDLING");
            Log.d("TOKEN MAGICO","FINISH IDLING");
        } catch (InterruptedException e) {
            // do nothing.
            System.out.println("TOKEN MAGICO - EXCEPTION " + e.getMessage());
        }
    }


    // We will see is the service is alive using this methods.
    @Override
    public void onCreate() {
        // Yes, whenever the service is alive, it will show the floating icon.
        System.out.println("TOKEN MAGICO - THE SERVICE IS ALIVE AND RUNNING ONCREATE");
        Log.d("TOKEN MAGICO","THE SERVICE IS ALIVE AND RUNNING ONCREATE");
        ServiceClient.fab.setVisibility(View.VISIBLE);
        super.onCreate();
    }

    @Override
    public void onDestroy() {
        // Yes, whenever the service dies, it will vanish the floating icon.
        System.out.println("TOKEN MAGICO - THE SERVICE DIES AND RUNNING ONDESTROY");
        Log.d("TOKEN MAGICO","THE SERVICE DIES AND RUNNING ONDESTROY");
        ServiceClient.fab.setVisibility(View.GONE);
        super.onDestroy();
    }
    /////////

    // Now, what is required for a bounded service

    private final IBinder binder = new LocalBinder();

    public class LocalBinder extends Binder {
        MyService getService() {
            return MyService.this;
        }
    }

    public void doSomething() {
        System.out.println("TOKEN MAGICO - THE SERVICE IS DOING SOMETHING IN BACKGROUND");
        Log.d("TOKEN MAGICO","THE SERVICE IS DOING SOMETHING IN BACKGROUND");
    }

    // onBind is the only requirement to extend Service.
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }


}
