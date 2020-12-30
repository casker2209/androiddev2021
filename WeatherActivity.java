package vn.edu.usth.weather;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.graphics.Bitmap;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class WeatherActivity extends AppCompatActivity {
    MediaPlayer mp;

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.weathermenu,menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()) {
            case R.id.refresh:
                /*final Handler handler = new Handler(Looper.myLooper()){
                    @Override
                    public void handleMessage(@NonNull Message msg) {
                        String content = msg.getData().getString("server_response");
                        Toast.makeText(getBaseContext(), content, Toast.LENGTH_SHORT).show();
                    }
                };
                Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(5000);
                        }
                        catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        Bundle bundle = new Bundle();
                        bundle.putString("server_response", "some sample json here");
                        Message msg = new Message();
                        msg.setData(bundle);
                        handler.sendMessage(msg);
                    }
                });
                thread.start();*/
                AsyncTask<String, Integer, Bitmap> task = new AsyncTask(){

                    @Override
                    protected Object doInBackground(Object[] objects) {
                        try {
                            Thread.sleep(5000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        return null;
                    }

                    @Override
                    protected void onPostExecute(Object o) {
                        Toast.makeText(getBaseContext(),"Executed",Toast.LENGTH_SHORT).show();
                    }
                };
                task.execute("http://ict.usth.edu.vn/wp-content/uploads/usth/usthlogo.png");

                return true;
            case R.id.newicon:
                Toast.makeText(WeatherActivity.this,(CharSequence)"This is a Toast.Refresh",Toast.LENGTH_LONG).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

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
        //Locale locale = new Locale("fr");
        //Locale.setDefault(locale);
        //Configuration config = getBaseContext().getResources().getConfiguration();
        //config.locale = locale;


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //mp = MediaPlayer.create(getBaseContext(),R.raw.merrychristmas);
        //mp.start();



        InputStream is = getResources().openRawResource(R.raw.merrychristmas);

        try {
            FileOutputStream fos = new FileOutputStream(Environment.getExternalStorageDirectory()+"/Android/data/merrychristmas.mp3");
            byte[] bytes = new byte[1024];
            int read = 0;
            while ((read = is.read(bytes)) > 0) {
                fos.write(bytes, 0, read);
            }
            is.close();
            fos.close();
            mp = new MediaPlayer();
            mp.setDataSource(Environment.getExternalStorageDirectory()+"/Android/data/merrychristmas.mp3");
            mp.start();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

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