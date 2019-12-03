package org.tensorflow.lite.examples.classification.fragments;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import org.tensorflow.lite.examples.classification.Bluetooth.Bluetooth;
//import org.tensorflow.lite.examples.classification.Compass.CompassActivity;
import org.tensorflow.lite.examples.classification.R;
import static android.content.Context.SENSOR_SERVICE;
import static androidx.core.content.ContextCompat.getSystemService;

/**
 * A simple {@link Fragment} subclass.
 */
public class ArduinoDataFragment extends Fragment implements SensorEventListener {

//    TextView DegreeTV;
//    private SensorManager SensorManage;

//    manager = (SensorManager) getApplication().getSystemService(Context.SENSOR_SERVICE);
//    compassSensor = manager.getDefaultSensor(Sensor.TYPE_ORIENTATION);

    public ArduinoDataFragment() {
        // Required empty public constructor
    }

    //Button compass;


    public static ArduinoDataFragment newInstance() {
        return new ArduinoDataFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_arduino_data, container, false);
//        DegreeTV = (TextView)view.findViewById(R.id.DegreeTV);
//        SensorManage = (SensorManager)view.getSystemService(SENSOR_SERVICE);
        Button arduinoFragbtn = (Button) view.findViewById(R.id.btnFrag);
        arduinoFragbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent uartIntent = new Intent(getActivity(), Bluetooth.class);
                startActivity(uartIntent);
            }
        });
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
//        // code for system's orientation sensor registered listeners
//        SensorManage.registerListener(this, SensorManage.getDefaultSensor(Sensor.TYPE_ORIENTATION),
//                SensorManager.SENSOR_DELAY_GAME);
    }

    @Override
    public void onPause() {
        super.onPause();
//        // to stop the listener and save battery
//        SensorManage.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {

//        // get angle around the z-axis rotated
//        float degree = Math.round(event.values[0]);
//
//        // assign variable for calculating Cardinal and Ordinal Directions
//        String cardinalOrdinalDirection = "null";
//
//        if (degree >= 350 || degree <= 10)
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
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
