package mcs.salazar.jesus.firstappforandroid;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import mcs.salazar.jesus.firstappforandroid.container.ListViewAdapter;
import mcs.salazar.jesus.firstappforandroid.model.Season;

public class FirstActivity extends AppCompatActivity {

    public static ArrayList<Season> THE_LIST = new ArrayList<>();

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        System.out.println("TOKEN MAGICO onSaveInstanceState is happening.");
        outState.putString("USERNAME","this may be a variable.");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);
        if (savedInstanceState != null &&
                savedInstanceState.getString("USERNAME") != null) {
            Toast.makeText(this, savedInstanceState.getString("USERNAME"), Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this,
                    "NO DATA RECOVERED",
                    Toast.LENGTH_LONG).show();
        }
        System.out.println("TOKEN MAGICO onCreate is happening.");

        if(THE_LIST.size() == 0) {
            loadData();
        }


        ListViewAdapter adapter = new ListViewAdapter(this, THE_LIST);
        ((ListView) findViewById(R.id.listview)).setAdapter(adapter);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Button button = findViewById(R.id.button_send);
        // button.setText(button.getText() + ", How are you?");

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(
                new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view,
                        "Show this from programatically attached onClick", Snackbar.LENGTH_LONG)
                        .show();
                // Explicit intent
                Intent intent = new Intent(FirstActivity.this, SecondActivity.class);
                intent.putExtra("RANDOM_STRING","This is a random String");
                //startActivityForResult(intent, 1);
                //startActivity(intent);
                startActivityForResult(intent, 1);
            }
        });
    }

    private void loadData() {
        THE_LIST.add(new Season("Friends",10,3,1));
        THE_LIST.add(new Season("Friends",13,1,2));
        THE_LIST.add(new Season("Friends",15,7,3));
        THE_LIST.add(new Season("Friends",10,3,4));
        THE_LIST.add(new Season("Friends",13,1,5));
        THE_LIST.add(new Season("Friends",15,7,6));
    }

    @Override
    protected void onStart() {
        super.onStart();
        System.out.println("TOKEN MAGICO onStart is happening.");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        System.out.println("TOKEN MAGICO onRestart is happening.");
    }

    @Override
    protected void onResume() {
        super.onResume();
        System.out.println("TOKEN MAGICO onResume is happening.");
    }

    @Override
    protected void onPause() {
        super.onPause();
        System.out.println("TOKEN MAGICO onPause is happening.");
    }

    @Override
    protected void onStop() {
        super.onStop();
        System.out.println("TOKEN MAGICO onStop is happening.");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        System.out.println("TOKEN MAGICO onDestroy is happening.");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_first, menu);
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

    public void thisIsAMethodSpecifiedFromXML(View view) {
        Toast.makeText(this,
                "Show this from thisIsAMethodSpecifiedFromXML",
                Toast.LENGTH_LONG).show();
        // startActivityForResult(intent, 2);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // The SecondActivity has the '1' codeRequest
        if (requestCode == 1) {
            switch(resultCode) {
                case 1:
                    // this may be the first 2 options
                    Toast
                            .makeText(this,
                                    "this may be the first 2 options", Toast.LENGTH_LONG)
                            .show();
                    break;
                case 2:
                    // this should be the 3rd option
                    Toast
                            .makeText(this,
                                    "this should be the 3rd option", Toast.LENGTH_LONG)
                            .show();
                    break;
                case 500:
                    // this should be the 4th option
                    Toast
                            .makeText(this,
                                    "this should be the 4th option", Toast.LENGTH_LONG)
                            .show();
                    break;
            }
        } else if (requestCode == 2) {
            // Make sure the request was successful
            if (resultCode == RESULT_OK) {
                // The user picked a contact.
                // The Intent's data Uri identifies which contact was selected.

                // Do something with the contact here (bigger example below)
            }
        }
    }

}
