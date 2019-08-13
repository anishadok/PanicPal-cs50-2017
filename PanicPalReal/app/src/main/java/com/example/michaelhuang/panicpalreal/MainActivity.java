package com.example.michaelhuang.panicpalreal;

// import libraries
import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    public TextView textView;

    // create global variables
    public static boolean isAttacked = false;
    public static double lat = 0.0;
    public static double lng = 0.0;

    // create sensor manager and sensor listener
    private SensorManager mSensorManager;
    private ShakeEventListener mSensorListener;

    // create location manager and location listener
    private LocationManager locmanager;
    private LocationListener loclistener;

    // This helps when we're requesting permission to send SMS.
    private static final int PERMISSION_SEND_SMS = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        // Make sensor manager and listener
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mSensorListener = new ShakeEventListener();

        // Check if we have GPS permissions
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION,Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.INTERNET}
                        ,10);
            }
            return;
        }

        // Request permissions to send SMS messages
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.SEND_SMS)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.SEND_SMS},
                    PERMISSION_SEND_SMS);

        }

        // Shake listener: If you shake it, it will enter into this piece of the code
        mSensorListener.setOnShakeListener(new ShakeEventListener.OnShakeListener() {

            @SuppressLint("MissingPermission")
            public void onShake() {
                // Send an SMS if shaken enough
                // referenced https://stackoverflow.com/questions/18437287/send-my-current-location-via-sms-android
                SmsManager smsManager = SmsManager.getDefault();
                String pnum = "5182388923";
                String msg = "Help my location is https://www.maps.google.com?q="+lat+","+lng;
                smsManager.sendTextMessage(pnum,null,msg,null, null);

                // displays a toast alerting user text was sent
                // referenced https://developer.android.com/guide/topics/ui/notifiers/toasts.html
                Context context = getApplicationContext();
                CharSequence text = "Text sent. Help is on the way!";
                int duration = Toast.LENGTH_LONG;

                Toast toast = Toast.makeText(context, text, duration);
                toast.show();
            }
        });

        // Create location manager and listener
        locmanager = (LocationManager) getSystemService(LOCATION_SERVICE);
        loclistener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                // If the location changes, then set the global variables lat and lng to latitude and longitude.
                lat = location.getLatitude();
                lng = location.getLongitude();
            }

            @Override
            public void onStatusChanged(String s, int i, Bundle bundle) {
                Log.d("Hi", "Location Status Changed");
            }

            @Override
            public void onProviderEnabled(String s) {
                Log.d("Hi", "Location Provider enabled");
            }

            @Override
            public void onProviderDisabled(String s) {
                Log.d("Hi", "Location Provider Disabled");
                Intent i = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(i);
            }
        };

        // Initiate checking for location inside the onCreate method.
        locmanager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 0, loclistener);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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


    @Override
    protected void onResume() {
        // referenced from https://stackoverflow.com/questions/2317428/android-i-want-to-shake-it
        super.onResume();
        mSensorManager.registerListener(mSensorListener,
                mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
                SensorManager.SENSOR_DELAY_UI);

    }
//
    @Override
    protected void onPause() {
        // referenced from https://stackoverflow.com/questions/2317428/android-i-want-to-shake-it
        mSensorManager.unregisterListener(mSensorListener);
        super.onPause();
    }
}
