package com.coolstuff.my.weatherforecast;

import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import static java.lang.String.valueOf;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnMapClickListener {

    private GoogleMap mMap;
    TextView textView;
    Marker marker;
    LatLng wantedLatlng;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

       textView = (TextView) findViewById(R.id.locinfo);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        myLocation(35.2218732,33.4170267, 15);

        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                wantedLatlng = latLng;
                myDialog();
                //   Toast.makeText(
                //          MainActivity.this,
                //          "Lat " + latLng.latitude + " "
                //                  + "Long " + latLng.longitude,
                //
            }
        });
    }

    public void myLocation(double lat, double lng, float zoom) {
        LatLng latLng = new LatLng(lat,lng);
        CameraUpdate update = CameraUpdateFactory.newLatLngZoom(latLng, zoom);
        mMap.moveCamera(update);


        MarkerOptions options = new MarkerOptions()
                .title(valueOf(latLng))
                .position(new LatLng(lat,lng))
                .draggable(true)
                .snippet("Your favourite marker is here");
        marker=mMap.addMarker(options);

    }


    @Override
    public void onMapClick(LatLng point) {

    }

    public void myDialog(){
        final Dialog showDialog = new Dialog(this);

        showDialog.setTitle("Weather");
        showDialog.setContentView(R.layout.dialog_layout);

        Button nobtn = (Button)showDialog.findViewById(R.id.no);

        nobtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog.cancel();
            }
        });

        Button yesbtn = (Button) showDialog.findViewById(R.id.yes);
        yesbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             //   MainActivity main = new MainActivity();
                MainActivity.myLat=wantedLatlng.latitude;
                MainActivity.myLng=wantedLatlng.longitude;

                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });

        showDialog.show();

    }
}
