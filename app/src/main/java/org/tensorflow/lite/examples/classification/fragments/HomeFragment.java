package org.tensorflow.lite.examples.classification.fragments;

import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import org.tensorflow.lite.Tensor;
import org.tensorflow.lite.TensorFlowLite;
import org.tensorflow.lite.examples.classification.Bluetooth.Bluetooth;
import org.tensorflow.lite.examples.classification.Bluetooth.DeviceListActivity;
import org.tensorflow.lite.examples.classification.R;
import org.tensorflow.lite.examples.classification.model.SensorDataObject;


public class HomeFragment extends Fragment {
    private OnFragmentInteractionListener mListener;

    private HomeViewModel homeViewModel;

    TextView accTV;
    TextView lightTV;
    TextView pressureTV;
    TextView ambientTempTV;
    TextView relativeHumidityTV;
    Button startMissionButton;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        final TextView battery = root.findViewById(R.id.battery);
        final TextView wifi = root.findViewById(R.id.wifi);
        accTV = root.findViewById(R.id.acc_values);
        lightTV = root.findViewById(R.id.light);
        pressureTV = root.findViewById(R.id.pressure);
        ambientTempTV = root.findViewById(R.id.ambientTemp);
        relativeHumidityTV = root.findViewById(R.id.relativeHumidity);

        homeViewModel.getBattery().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                battery.setText(s);
            }
        });

        homeViewModel.getWifi().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                wifi.setText(s);
            }
        });

        homeViewModel.getSensorDO().observe(this, new Observer<SensorDataObject>(){

            @Override
            public void onChanged(SensorDataObject dataObject) {
                accTV.setText(getString(R.string.acc_format,dataObject.getAccx(),dataObject.getAccy(),dataObject.getAccz()));
                lightTV.setText(getString(R.string.light_format, dataObject.getLight()));
                pressureTV.setText(getString(R.string.pressure_format, dataObject.getPressure()));
                ambientTempTV.setText(getString(R.string.ambient_temp_format, dataObject.getAmbient_temp()));
                relativeHumidityTV.setText(getString(R.string.relative_humidity_format, dataObject.getRelativeHumidity()));
            }
        });

        homeViewModel.getIsMission().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if(aBoolean){
                    startMissionButton.setText("Stop Mission");
                }else{
                    startMissionButton.setText("Start Mission");
                }
            }
        });

        startMissionButton = root.findViewById(R.id.start_mission_button);
        startMissionButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                mListener.onStartMissionButtonPressed();
            }
        });

        startMissionButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                mListener.onStartMissionButtonPressed();
            }
        });

        return root;
    }

    @Override
    public void onResume() {
        super.onResume();
        homeViewModel.activatePhoneSensors();
    }

    @Override
    public void onPause() {
        super.onPause();
        homeViewModel.stopPhoneSensors();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;

        homeViewModel.onDetached();
    }

    public void onStartMission(int objectCount, int minutes) {
        homeViewModel.onStartMission(objectCount,minutes);
    }

    public interface OnFragmentInteractionListener {
        void onStartMissionButtonPressed();
    }

}