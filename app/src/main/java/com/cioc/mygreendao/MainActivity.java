package com.cioc.mygreendao;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.cioc.mygreendao.db.DaoSession;
import com.cioc.mygreendao.db.GPSLocation;
import com.cioc.mygreendao.db.GPSLocationDao;

import org.greenrobot.greendao.query.Query;

import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    Button getLocationBtn;
    TextView locationText, longitude, latitude;

    String LOG_TAG = "onDestroy(><><><><)";
    LocationManager locationManager;
    GPSLocationDao gpsLocationDao;
    Query<GPSLocation> gpsLocationQuery;
    LocationAdapter locationAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_main);
        startService(new Intent(this,LocationService.class));
        // get the note DAO
        DaoSession daoSession = ((AppController) getApplication()).getDaoSession();
        gpsLocationDao = daoSession.getGPSLocationDao();

        // query all notes, sorted a-z by their text
        gpsLocationQuery = gpsLocationDao.queryBuilder().orderAsc(GPSLocationDao.Properties.Id).build();
        locationText = findViewById(R.id.locationText);


        if (ContextCompat.checkSelfPermission(getApplicationContext(),
                android.Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(getApplicationContext(),
                        android.Manifest.permission.ACCESS_COARSE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this,
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION,
                    android.Manifest.permission.ACCESS_COARSE_LOCATION}, 101);
        }
        setUpViews();
        updateLocation();
//        getLocation();

//        getLocationBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                getLocation();
//            }
//        });
    }

    private void updateLocation() {
        List<GPSLocation> locations = gpsLocationQuery.list();
        locationAdapter.setGPSLocation(locations);
    }

    protected void setUpViews() {
        RecyclerView recyclerView = findViewById(R.id.rvLocation);
        //noinspection ConstantConditions
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        locationAdapter = new LocationAdapter(this);
        recyclerView.setAdapter(locationAdapter);
    }

//    public void getLocation() {
//        try {
//            locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
//            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 5000, 0, this);
//        }
//        catch(SecurityException e) {
//            e.printStackTrace();
//        }
//    }
//
//    @Override
//    public void onLocationChanged(Location location) {
//        longitude = new TextView(this);
//        longitude.setText(location.getLongitude()+"");
//        latitude = new TextView(this);
//        latitude.setText(location.getLatitude()+"");
//        locationText.setText("Latitude: " + location.getLatitude() + "\n Longitude: " + location.getLongitude());
//
//        try {
//            Geocoder geocoder = new Geocoder(this, Locale.getDefault());
//            List<Address> addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
//            String loc = locationText.getText() + "\n"+addresses.get(0).getAddressLine(0)+"\n" +
//                    addresses.get(0).getAddressLine(1);//+addresses.get(0).getAddressLine(2);
//            locationText.setText(loc);
//            addLocation();
//        }catch(Exception e)
//        {
//
//        }
//
//    }
//
//    public void addLocation() {
//        GPSLocation gps = new GPSLocation();
//        gps.setLongitude_value(longitude.getText().toString());
//        gps.setLatitude_value(latitude.getText().toString());
//        gpsLocationDao.insert(gps);
//        Log.e("DaoExample", "Inserted new gspLocation, ID: " + gps.getId());
//    }
//
//    @Override
//    public void onProviderDisabled(String provider) {
//        ImageView iv = new ImageView(this);
//        iv.setImageResource(R.drawable.gps_off);
//
//        Toast t = new Toast(this);
//        t.setView(iv);
//        t.setDuration(Toast.LENGTH_SHORT);
//        t.show();
//
//        Toast.makeText(this, "GPS OFF", Toast.LENGTH_SHORT).show();
//    }
//
//    @Override
//    public void onStatusChanged(String provider, int status, Bundle extras) {
//
//    }
//
//    @Override
//    public void onProviderEnabled(String provider) {
//        ImageView iv = new ImageView(this);
//        iv.setImageResource(R.drawable.gps_on);
//
//        Toast t = new Toast(this);
//        t.setView(iv);
//        t.setDuration(Toast.LENGTH_SHORT);
//        t.show();
//
//        Toast.makeText(this, "GPS ON", Toast.LENGTH_SHORT).show();
//    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.w(LOG_TAG, "------------------------------------------ Destroyed Notification Service");
        Intent intent = new Intent("com.android.techtrainner");
        intent.putExtra("yourvalue", "torestore");
        sendBroadcast(intent);
    }
}