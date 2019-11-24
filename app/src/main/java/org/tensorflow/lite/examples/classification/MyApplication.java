package org.tensorflow.lite.examples.classification;

import android.app.Application;

import com.mapbox.mapboxsdk.Mapbox;

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Mapbox.getInstance(getApplicationContext(), getString(R.string.mapbox_access_token));

        //Starts Bluetooth
      //  Intent intent = new Intent(getApplicationContext(), Bluetooth.class);
      //  startActivity(intent);
    }
}
