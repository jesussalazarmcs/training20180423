package mcs.salazar.jesus.firstappforandroid;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import mcs.salazar.jesus.firstappforandroid.container.RecyclerViewAdapter;
import mcs.salazar.jesus.firstappforandroid.fragment.EpisodeListActivity;

public class SecondActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);


        RecyclerViewAdapter adapter =
                new RecyclerViewAdapter(this, FirstActivity.THE_LIST);
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(llm);
        recyclerView.setAdapter(adapter);



        //TextView textView = findViewById(R.id.text_on_header_of_drawer);
        TextView textView = findViewById(R.id.text_on_header_of_drawer);
        String stringTemporal = getIntent().getStringExtra("RANDOM_STRING");
        if (stringTemporal != null) {
            if (textView != null) {
                textView.setText(stringTemporal);
            } else {
                Toast
                        .makeText(this, stringTemporal, Toast.LENGTH_LONG)
                        .show();
            }
        }


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(
                        EpisodeListActivity.getIntent(SecondActivity.this)
                );
            }
        });

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
        getMenuInflater().inflate(R.menu.second, menu);
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

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            Intent result = new Intent("com.example.RESULT_ACTION", Uri.parse("content://nav_camera"));
            setResult(1, result);
            finish();
        } else if (id == R.id.nav_gallery) {
            Intent result = new Intent("com.example.RESULT_ACTION", Uri.parse("content://nav_gallery"));
            setResult(1, result);
            finish();
        } else if (id == R.id.nav_slideshow) {
            Intent result = new Intent("com.example.RESULT_ACTION", Uri.parse("content://nav_slideshow"));
            setResult(2, result);
            finish();
        } else if (id == R.id.nav_manage) {
            Intent result = new Intent("com.example.RESULT_ACTION", Uri.parse("content://nav_manage"));
            setResult(500, result);
            finish();
        }/* else if (id == R.id.nav_share) {
            Intent result = new Intent("com.example.RESULT_ACTION", Uri.parse("content://result_uri"));
            setResult(Activity.RESULT_OK, result);
            finish();
        } else if (id == R.id.nav_send) {
            Intent result = new Intent("com.example.RESULT_ACTION", Uri.parse("content://result_uri"));
            setResult(Activity.RESULT_OK, result);
            finish();
        }*/

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
