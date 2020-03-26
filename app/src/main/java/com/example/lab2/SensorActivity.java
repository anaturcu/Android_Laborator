package com.example.lab2;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;

import java.util.List;

public class SensorActivity extends Activity implements SensorEventListener {
    private SensorManager sensorManager;
    private List<Sensor> sensors;
    private TextView light;
    private TextView gyroscope;
    private TextView accelerometer;
    private TextView pressure;
    private TextView latitude;
    private TextView longitude;
    private LocationManager locationManager;
    private Location location;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public final void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sensor);
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        sensors = sensorManager.getSensorList(Sensor.TYPE_ALL);
        light = findViewById(R.id.light);
        latitude = findViewById(R.id.latitude);
        longitude = findViewById(R.id.longitude);
        gyroscope = findViewById(R.id.gyroscope);
        accelerometer = findViewById(R.id.accelerometer);
        pressure = findViewById(R.id.pressure);
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},1);

        }
        if (checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},1);

        }
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 4000, 5, new LocationListener() {



                @Override
                public void onLocationChanged(Location location) {
                    latitude.setText(String.valueOf( location.getLatitude()));
                    longitude.setText(String.valueOf(location.getLongitude()));
                }
                public void onStatusChanged(String provider, int status, Bundle extras) {

                }

                @Override
                public void onProviderEnabled(String provider) {

                }

                @Override
                public void onProviderDisabled(String provider) {

                }
            });


    }

    @Override
    public final void onAccuracyChanged(Sensor sensor, int accuracy) {
        // Do something here if sensor accuracy changes.
    }

    @Override
    public final void onSensorChanged(SensorEvent event) {
        if(event.sensor.getType()==Sensor.TYPE_LIGHT) {
            float lux = event.values[0];
            light.setText(String.valueOf(lux)+" lx");
        }
        if(event.sensor.getType()==Sensor.TYPE_GYROSCOPE) {
            float lux_x = event.values[0];
            float lux_y = event.values[1];
            float lux_z = event.values[2];
            gyroscope.setText("X:"+String.valueOf(lux_x)+ " Y:"+String.valueOf(lux_y)+ " Z:"+String.valueOf(lux_z)+ " ");
        }
        if(event.sensor.getType()==Sensor.TYPE_ACCELEROMETER) {
            float lux_x = event.values[0];
            float lux_y = event.values[1];
            float lux_z = event.values[2];
            accelerometer.setText("X:"+String.valueOf(lux_x)+ " Y:"+String.valueOf(lux_y)+ " Z:"+String.valueOf(lux_z)+ " ");
        }
        if(event.sensor.getType()==Sensor.TYPE_PRESSURE) {
            float lux = event.values[0];
            pressure.setText(String.valueOf(lux)+" hPa");
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        for (Sensor sensor: sensors)
            sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        super.onPause();
        for (Sensor sensor: sensors)
            sensorManager.unregisterListener(this);
    }
}