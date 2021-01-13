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
import android.graphics.BitmapFactory;
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
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.tabs.TabLayout;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;


public class WeatherActivity extends AppCompatActivity {
    static RequestQueue rq;
    ViewPager vpPager;
    MediaPlayer mp;
    HttpsURLConnection connection;
    WeatherAdapter adapterViewPager;
    URL url;
    String weather;
    String city;
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
                getWeatherAPI();
                Response.Listener<Bitmap> listener = new Response.Listener<Bitmap>(){
                    @Override
                    public void onResponse(Bitmap response) {
                        ImageView iv = (ImageView) findViewById(R.id.icon2);
                        iv.setImageBitmap(response);
                    }
                };
                ImageRequest imageRequest = new ImageRequest(
                        "https://usth.edu.vn/uploads/chuong-trinh/2017_01/logo-moi_2.png",
                        listener, 0, 0, ImageView.ScaleType.CENTER,
                        Bitmap.Config.ARGB_8888,null);
                rq.add(imageRequest);

                AsyncTask<String, Integer, Bitmap> task = new AsyncTask(){

                    @Override
                    protected Object doInBackground(Object[] objects) {
                        try {
                            url = new URL("https://usth.edu.vn/uploads/chuong-trinh/2017_01/logo-moi_2.png");
                            connection =
                                    (HttpsURLConnection) url.openConnection();
                            connection.setRequestMethod("GET");
                            connection.setDoInput(true);
                            connection.connect();
                            int response = connection.getResponseCode();
                            Log.i("USTHWeather", "The response is: " + response);
                            InputStream is = connection.getInputStream();
                            Bitmap bitmap = BitmapFactory.decodeStream(is);
                            return bitmap;

                        } catch (MalformedURLException | ProtocolException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        return null;
                    }

                    @Override
                    protected void onPostExecute(Object o) {
                        Bitmap bitmap = (Bitmap) o;
                        ImageView logo = (ImageView) findViewById(R.id.icon1);
                        logo.setImageBitmap(bitmap);
                        connection.disconnect();
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


    public void getWeatherAPI(){
        String input = adapterViewPager.getPageTitle(vpPager.getCurrentItem()).toString();
        rq = Volley.newRequestQueue(this);
        String url = "https://community-open-weather-map.p.rapidapi.com/weather?q="+input+"&lat=0&lon=0&id=2172797&lang=null&units=metric&mode=xml%2C%20html";
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                TextView ___weather = (TextView) getSupportFragmentManager().getFragments().get(vpPager.getCurrentItem()).getView().findViewById(R.id.weather);
                TextView ___city = (TextView) getSupportFragmentManager().getFragments().get(vpPager.getCurrentItem()).getView().findViewById(R.id.city);


                try {
                    weather = response.getJSONArray("weather").getJSONObject(0).getString("main")
                            +"\n" +response.getJSONObject("main").getString("temp")+"C";
                    city = response.getString("name");
                    ___weather.setText(weather);
                    ___city.setText(city);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                //headers.put("Content-Type", "application/json");
                headers.put("x-rapidapi-key", "6855de1677mshbac36bd7916394ep199e40jsna5072934982b");
                headers.put("x-rapidapi-host","community-open-weather-map.p.rapidapi.com");
                return headers;
            }
        };
        rq.add(request);
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
            switch(position){
                case 0:
                    return "Hanoi,vn";
                case 1:
                    return "Paris,fr";
                case 2:
                    return "Tokyo,jp";
            }
        return null;
        }

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //Locale locale = new Locale("fr");
        //Locale.setDefault(locale);
        //Configuration config = getBaseContext().getResources().getConfiguration();
        //config.locale = locale;
        rq =  Volley.newRequestQueue(WeatherActivity.this);

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
        vpPager = (ViewPager) findViewById(R.id.vpPager);
        adapterViewPager = new WeatherAdapter(getSupportFragmentManager());
        vpPager.setOffscreenPageLimit(3);
        vpPager.setAdapter(adapterViewPager);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab);
        tabLayout.setupWithViewPager(vpPager);
        getWeatherAPI();
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