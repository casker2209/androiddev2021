package vn.edu.usth.weather;

import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.os.Bundle;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

public class WeatherActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);
        Log.i("Created","onCreate");

        //TextView mTextView = (TextView) findViewById(R.id.text_message);
        //mTextView.setText("Hello World!");
        LinearLayout Layout = (LinearLayout) findViewById(R.id.forecastfraglayout);
        Layout.setOrientation(LinearLayout.VERTICAL);
        //FrameLayout frame = new FrameLayout(this);
        if (savedInstanceState == null) {

            ForecastFragment firstFragment = new ForecastFragment();
            getSupportFragmentManager().beginTransaction().add(
                    R.id.forecastfraglayout, firstFragment).commit();
        }
    }
    @Override
    protected void onStop() {
        super.onStop();
        Log.i("Stopped","onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i("Destroyed","onDestroy");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i("Paused","onPause");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i("Resumed","onResume");      }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i("Started","onStart");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.i("Restarted","onRestart");
    }
}