package org.tensorflow.lite.examples.classification.fragments;

import android.app.Application;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.BatteryManager;
import android.os.Build;
import android.os.CountDownTimer;
import android.util.Log;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import org.tensorflow.lite.examples.classification.model.SensorDataObject;
import org.tensorflow.lite.examples.classification.servicenow.AsyncTaskTableTes4;
import org.tensorflow.lite.examples.classification.sqlite.SensorReaderDbHelper;

import static android.content.Context.BATTERY_SERVICE;

public class HomeViewModel extends AndroidViewModel implements SensorEventListener {

    private MutableLiveData<String> mBattery;
    private MutableLiveData<String> mBattery_percentage;
    private MutableLiveData<String> mWifi;
    private MutableLiveData<SensorDataObject> dataObjectMutableLiveData;
    private MutableLiveData<Boolean> isMission;

    private CountDownTimer missionTimer;
    private SensorDataObject sensorDO;
    private SensorReaderDbHelper dbHelper;

    // capture sensor details when there is an active mission
    private SensorManager manager;
    private Sensor accSensor;
    private Sensor lightSensor;
    private Sensor pressureSensor;
    private Sensor ambientTempSensor;
    private Sensor relativeHumiditySensor;
    private Sensor compassSensor;

    public HomeViewModel(Application context) {
        super(context);
        mBattery = new MutableLiveData<>();
        mBattery_percentage = new MutableLiveData<>();
        mWifi = new MutableLiveData<>();
        dataObjectMutableLiveData = new MutableLiveData<>();
        isMission = new MutableLiveData<>();

        dbHelper = new SensorReaderDbHelper(context);

        BatteryManager bm = (BatteryManager) context.getSystemService(BATTERY_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            int percentage = bm.getIntProperty(BatteryManager.BATTERY_PROPERTY_CAPACITY);
            mBattery.setValue("Battery Percentage: " + percentage + "%");
            mBattery_percentage.setValue(percentage+"");

        }

        ConnectivityManager connectionManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo wifiCheck = connectionManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);

        if (wifiCheck.isConnected()) {
            mWifi.setValue("WiFi: Connected");
        } else {
            mWifi.setValue("Wifi: Not Connected");
        }
    }

    public LiveData<String> getBattery() {
        return mBattery;
    }

    public LiveData<String> getWifi() {
        return mWifi;
    }

    public LiveData<SensorDataObject> getSensorDO() {
        return dataObjectMutableLiveData;
    }

    public MutableLiveData<Boolean> getIsMission() {
        return isMission;
    }

    public void activatePhoneSensors() {
        manager = (SensorManager) getApplication().getSystemService(Context.SENSOR_SERVICE);
        accSensor = manager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        lightSensor = manager.getDefaultSensor(Sensor.TYPE_LIGHT);
        pressureSensor = manager.getDefaultSensor(Sensor.TYPE_PRESSURE);
        relativeHumiditySensor = manager.getDefaultSensor(Sensor.TYPE_RELATIVE_HUMIDITY);
        ambientTempSensor = manager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE);
        compassSensor = manager.getDefaultSensor(Sensor.TYPE_ORIENTATION);

        sensorDO = new SensorDataObject();

        boolean isavailable = manager.registerListener(this, accSensor, SensorManager.SENSOR_DELAY_NORMAL);
        Log.i(HomeViewModel.class.getSimpleName(), "Accelerometer "+isavailable);
        isavailable = manager.registerListener(this, lightSensor, SensorManager.SENSOR_DELAY_NORMAL);
        Log.i(HomeViewModel.class.getSimpleName(), "Light Sensor "+isavailable);
        isavailable = manager.registerListener(this, pressureSensor, SensorManager.SENSOR_DELAY_NORMAL);
        Log.i(HomeViewModel.class.getSimpleName(), "Pressure "+isavailable);
        isavailable = manager.registerListener(this, relativeHumiditySensor, SensorManager.SENSOR_DELAY_NORMAL);
        Log.i(HomeViewModel.class.getSimpleName(), "Relative Humidity "+isavailable);
        isavailable = manager.registerListener(this, ambientTempSensor, SensorManager.SENSOR_DELAY_NORMAL);
        Log.i(HomeViewModel.class.getSimpleName(), "Ambient Temperature "+isavailable);
        isavailable = manager.registerListener(this, compassSensor, SensorManager.SENSOR_DELAY_GAME);
        Log.i(HomeViewModel.class.getSimpleName(), "Compass "+isavailable);
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        // update the sensor object. the object carries latest sensor value by the time it saves to the database.
        float[] values = sensorEvent.values;
//        float degree = Math.round(event.values[0]);
        switch (sensorEvent.sensor.getType()) {
            case Sensor.TYPE_ACCELEROMETER:
                sensorDO.setAccx(values[0]);
                sensorDO.setAccy(values[1]);
                sensorDO.setAccz(values[2]);
                sensorDO.setU_battery_level(Float.parseFloat(mBattery_percentage.getValue()));
                break;
            case Sensor.TYPE_LIGHT:
                sensorDO.setLight(values[0]);
                break;
            case Sensor.TYPE_PRESSURE:
                sensorDO.setPressure(values[0]);
                break;
            case Sensor.TYPE_AMBIENT_TEMPERATURE:
                sensorDO.setAmbient_temp(values[0]);
                break;
            case Sensor.TYPE_RELATIVE_HUMIDITY:
                sensorDO.setRelativeHumidity(values[0]);
                break;
            case Sensor.TYPE_ORIENTATION:
                sensorDO.setCompass(values[0]);
        }
        dataObjectMutableLiveData.postValue(sensorDO);

//        String cardinalOrdinalDirection = "null";
//
//        if (sensorDO.setCompass(values[0]) >= 350 || sensorDO.setCompass(values[0]) <= 10)
//            cardinalOrdinalDirection = "N";
//        if (degree < 350 && degree > 280)
//            cardinalOrdinalDirection = "NW";
//        if (degree <= 280 && degree > 260)
//            cardinalOrdinalDirection = "W";
//        if (degree <= 260 && degree > 190)
//            cardinalOrdinalDirection = "SW";
//        if (degree <= 190 && degree > 170)
//            cardinalOrdinalDirection = "S";
//        if (degree <= 170 && degree > 100)
//            cardinalOrdinalDirection = "SE";
//        if (degree <= 100 && degree > 80)
//            cardinalOrdinalDirection = "E";
//        if (degree <= 80 && degree > 10)
//            cardinalOrdinalDirection = "NE";
//
//        DegreeTV.setText("Direction: " + cardinalOrdinalDirection + "\nDegrees: " + Float.toString(degree) + "°");
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    public void stopPhoneSensors() {
        if (manager != null) {
            manager.unregisterListener(this, accSensor);
            manager.unregisterListener(this, lightSensor);
            manager.unregisterListener(this, pressureSensor);
            manager.unregisterListener(this, ambientTempSensor);
            manager.unregisterListener(this, relativeHumiditySensor);
            manager.unregisterListener(this, compassSensor);
        }
    }

    public void onStartMission(int objectCount, int minutes) {
        missionTimer = new CountDownTimer(minutes * 60 * 1000, 30 * 1000) {

            @Override
            public void onTick(long l) {
                // will be called every interval
                // save to SQLite
                SensorDataObject sdo = sensorDO;
                sensorDO = new SensorDataObject();

                long id = dbHelper.insertSensorData(sdo);
                Log.i(HomeViewModel.class.getSimpleName(), "Save sensor data " + sdo.toString());

                // send to service now
                new AsyncTaskTableTes4(sdo).execute();
            }

            @Override
            public void onFinish() {
                missionTimer = null;
                isMission.postValue(false);
            }
        };
        missionTimer.start();
        isMission.postValue(true);
    }

    public void onDetached() {
        if(missionTimer!=null){
            missionTimer.cancel();
            missionTimer=null;
        }
    }
}