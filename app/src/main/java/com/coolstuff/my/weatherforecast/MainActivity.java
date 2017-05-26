package com.coolstuff.my.weatherforecast;

import android.content.Intent;
import android.icu.text.DateFormat;
import android.icu.text.SimpleDateFormat;
import android.icu.util.Calendar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.vision.barcode.Barcode;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.Date;

import static java.security.AccessController.getContext;


public class MainActivity extends AppCompatActivity {
    TextView tempTextView;
    TextView conditionTextView;
    TextView cityTextView;
  //  TextView dateTextView;
    ImageView weatherImageView;
    Button map;
    TextView humidityTextView;
    TextView windSpeedTextView;
    TextView maxTextView;
    TextView minTextView;

    public static double myLat=0;
    public static double myLng=0;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tempTextView = (TextView) findViewById(R.id.tempTextView);
        conditionTextView = (TextView) findViewById(R.id.conditionTextView);
        cityTextView = (TextView) findViewById(R.id.cityTextView);
       // dateTextView = (TextView) findViewById(R.id.dateTextView);
        weatherImageView = (ImageView) findViewById(R.id.weatherImageView);
        humidityTextView = (TextView) findViewById(R.id.humidityTextView);
        windSpeedTextView = (TextView) findViewById(R.id.windSpeedTextView);
        maxTextView = (TextView) findViewById(R.id.maxTextView);
        minTextView = (TextView) findViewById(R.id.minTextView);
        map = (Button) findViewById(R.id.map);


        map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, MapsActivity.class);
                startActivity(i);
            }
        });


       // tempTextView.setText("25");


        String url = "http://api.openweathermap.org/data/2.5/weather?lat="+String.valueOf(myLat)+"&lon="+String.valueOf(myLng)+"&appid=e2b7fc94c355709e226f0bd64b8a1a89";

        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject responseObject) {

                        Log.v("WEATHER", "Response: " + responseObject.toString());

                        try {
                            JSONObject mainJSONObject = responseObject.getJSONObject("main");
                            JSONArray weatherArray = responseObject.getJSONArray("weather");
                            JSONObject firstWeatherObject = weatherArray.getJSONObject(0);
                            JSONObject windJSONObject = responseObject.getJSONObject("wind");


                            String kelvin = Integer.toString((int) Math.round (mainJSONObject.getDouble("temp")));
                            int result = Integer.parseInt(kelvin);
                            int celcius = (int)(result-273);
                            String temp = Integer.toString(celcius);

                            String minTemp = Integer.toString((int) Math.round (mainJSONObject.getDouble("temp_min")));
                            int resultMin = Integer.parseInt(minTemp);
                            int celciusMin = (int)(resultMin-273);
                            String tempMin = Integer.toString(celciusMin);


                            String maxTemp = Integer.toString((int) Math.round (mainJSONObject.getDouble("temp_max")));
                            int resultMax = Integer.parseInt(maxTemp);
                            int celciusMax = (int)(resultMax-273);
                            String tempMax = Integer.toString(celciusMax);

                            String humidity = mainJSONObject.getString("humidity");
                            String weatherDescription = firstWeatherObject.getString("description");
                            String city = responseObject.getString("name");
                            String wind = windJSONObject.getString("speed");

                            tempTextView.setText(temp);
                            conditionTextView.setText(weatherDescription);
                            cityTextView.setText(city);
                            humidityTextView.setText("Humidity "+humidity+"%");
                            minTextView.setText("Min Temp "+tempMin);
                            maxTextView.setText("Max Temp "+tempMax);
                            windSpeedTextView.setText("Wind Speed "+wind+" m/s");

                            int iconResourceId = getResources().getIdentifier("icon_" + weatherDescription.replace(" ", ""), "drawable", getPackageName());
                            weatherImageView.setImageResource(iconResourceId);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO Auto-generated method stub

                    }


                });

// Access the RequestQueue through your singleton class.
        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(jsObjRequest);
    }

}
