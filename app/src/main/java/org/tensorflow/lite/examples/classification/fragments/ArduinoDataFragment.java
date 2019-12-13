package org.tensorflow.lite.examples.classification.fragments;


import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import org.tensorflow.lite.examples.classification.Bluetooth.Bluetooth;
import org.tensorflow.lite.examples.classification.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ArduinoDataFragment extends Fragment {

    public ArduinoDataFragment() {
        // Required empty public constructor
    }

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
        Button arduinoFragbtn = (Button) view.findViewById(R.id.btnFrag);
        arduinoFragbtn.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), Bluetooth.class);
            startActivity(intent);
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
    }

    @Override
    public void onPause() {
        super.onPause();
    }
}
