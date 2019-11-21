package org.tensorflow.lite.examples.classification.fragments;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.tensorflow.lite.examples.classification.R;
import org.tensorflow.lite.examples.classification.model.SensorDataObject;
import org.tensorflow.lite.examples.classification.sqlite.SensorReaderDbHelper;

import java.util.List;

public class SensorsDataFragment extends Fragment {

    public SensorsDataFragment() {
        // Required empty public constructor
    }

    public static SensorsDataFragment newInstance() {
        return new SensorsDataFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sensors_data, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RecyclerView recyclerView = view.findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        List<SensorDataObject> list = new SensorReaderDbHelper(getContext()).getSensorData();
        recyclerView.setAdapter(new SensorDataAdapter(getContext(),list));
    }
}
