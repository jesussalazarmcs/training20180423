package mcs.salazar.jesus.reactivejavademo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onClick(View view) {
        String text = ((TextView) view).getText().toString();
        switch(text){
            case "First":
                startActivity(RxJavaSimpleActivity.getIntent(this));
                break;
            case "Second":
                startActivity(ColorsActivity.getIntent(this));
                break;
            case "Third":
                startActivity(BooksActivity.getIntent(this));
                break;
            default:
                break;
        }
    }
}
