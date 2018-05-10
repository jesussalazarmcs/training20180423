package mcs.salazar.jesus.servicesdemo;

import android.content.ServiceConnection;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.util.Log;
import android.widget.Toast;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.content.Intent;
import android.content.Context;
import android.os.IBinder;
import android.content.ComponentName;

public class ServiceClient extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, ServiceConnection {

    public static FloatingActionButton fab;
    public static final String THREAD_KEY = "SAMETHREAD";
    public static boolean startSticky = true;
    public static boolean sameThread = true;
    public static Intent service = null;
    public static MyService.LocalBinder binder = null;
    // Please try always to avoid having a reference to contexts
    // this one is just for educational purpuse.
    public static Context context = null;

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // First button ON/OFF THREAD
            switchThread();
        } else if (id == R.id.nav_gallery) {
            // Second button Start Service
            startService();
        } else if (id == R.id.nav_slideshow) {
            // Third button Start Service from another Thread
            startServiceFromAnotherThread();
        } else if (id == R.id.nav_manage) {
            // Fourth button Stop Started Service
            stopService();
        } else if (id == R.id.nav_share) {
            // Fifth button Bind To Service
            bindToService();
        } else if (id == R.id.nav_send) {
            // Last button Unbind To Service
            unBindToService();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void switchThread() {
        if (sameThread) {
            sameThread = false;
            System.out.println("TOKEN MAGICO sameThread WAS TURNED OFF");
            Toast.makeText(this, "sameThread WAS TURNED OFF", Toast.LENGTH_LONG).show();
        } else {
            sameThread = true;
            System.out.println("TOKEN MAGICO sameThread WAS TURNED ON");
            Toast.makeText(this, "sameThread WAS TURNED ON", Toast.LENGTH_LONG).show();
        }
    }

    private void startService() {
        initService();
        startService(service);
    }

    private void initService() {
        if (service == null) {
            service = new Intent(this, MyService.class);
            service.putExtra(THREAD_KEY, sameThread);
        }
    }

    private void startServiceFromAnotherThread() {
        if (context == null) {
            // Try to avoid doing this.
            context = this;
        }
        // Again, keep track of your threads, do not use anonymous as I am doing.
        new Thread() {
            public void run() {
                service = new Intent(context, MyService.class);
                service.putExtra(THREAD_KEY, sameThread);
                startService(service);
            }
        }.start();
    }

    private void stopService() {
        if (service != null) {
            stopService(service);
            service = null;
        }
    }

    private void bindToService() {
        initService();
        if (binder == null) {
            System.out.println("TOKEN MAGICO BINDING TO SERVICE");
            Toast.makeText(this, "BINDING TO SERVICE", Toast.LENGTH_LONG).show();
            bindService(service,this,Context.BIND_AUTO_CREATE);
        } else {
            // If this is reached, the service should exists and we should have the binder already.
            System.out.println("TOKEN MAGICO DOING SOMETHING ON BINDED SERVICE");
            Toast.makeText(this, "DOING SOMETHING ON BINDED SERVICE", Toast.LENGTH_LONG).show();
            binder.getService().doSomething();
        }
    }

    private void unBindToService() {
        unbindService(this);
    }

    @Override
    public void onServiceConnected(ComponentName name, IBinder binder) {
        this.binder = (MyService.LocalBinder) binder;
    }

    @Override
    public void onServiceDisconnected(ComponentName name) {
        binder = null;
    }

    @Override
    protected void onPause() {
        super.onPause();
        unBindToService();
    }

    // You do not really need to put attention to the following.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_client);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setVisibility(View.GONE);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.service_client, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
