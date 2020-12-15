package vn.edu.usth.weather;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.res.Configuration;
import android.util.Log;
import android.os.Bundle;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;

import java.util.Locale;

public class WeatherActivity extends AppCompatActivity {
    public static class WeatherAdapter extends FragmentPagerAdapter{
        private static int NUM_ITEMS = 3;
        public WeatherAdapter(FragmentManager fragmentManager) {
            super(fragmentManager);
        }
        @Override
        public int getCount() {
            return NUM_ITEMS;
        }
        @Override
        public Fragment getItem(int page){
            switch (page){
                case 0:
                    return WeatherAndForecastFragment.newInstance();
                case 1:
                    return WeatherAndForecastFragment.newInstance();
                case 2:
                    return WeatherAndForecastFragment.newInstance();

            }
            return null;
        }
        @Override
        public CharSequence getPageTitle(int position) {
            return "Page" + position;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Locale locale = new Locale("fr");
        Locale.setDefault(locale);
        Configuration config = getBaseContext().getResources().getConfiguration();
        config.locale = locale;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);
        Log.i("Created","onCreate");
        ViewPager vpPager = (ViewPager) findViewById(R.id.vpPager);
        WeatherAdapter adapterViewPager = new WeatherAdapter(getSupportFragmentManager());
        vpPager.setOffscreenPageLimit(3);
        vpPager.setAdapter(adapterViewPager);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab);
        tabLayout.setupWithViewPager(vpPager);

        //TextView mTextView = (TextView) findViewById(R.id.text_message);
        //mTextView.setText("Hello World!");
        //LinearLayout Layout = (LinearLayout) findViewById(R.id.forecastfraglayout);
        //Layout.setOrientation(LinearLayout.VERTICAL);
        //FrameLayout frame = new FrameLayout(this);
        if (savedInstanceState == null) {

            //ForecastFragment firstFragment = new ForecastFragment();
            //getSupportFragmentManager().beginTransaction().add(
            //        R.id.forecastfraglayout, firstFragment).commit();
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