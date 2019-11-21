package org.tensorflow.lite.examples.classification.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import org.tensorflow.lite.examples.classification.R;
import org.tensorflow.lite.examples.classification.tflite.Classifier.Recognition;

/**
 * A placeholder fragment containing a simple view.
 */
public class CameraFragment extends Fragment {

    TextView object;
    TextView confidence;
    View foundView;

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_camera, container, false);
        object = root.findViewById(R.id.detected_item);
        confidence = root.findViewById(R.id.detected_item_value);
        foundView = root.findViewById(R.id.found);
        return root;
    }

    public void onItemDetected(Recognition recognition) {
        if (recognition != null) {
            if (recognition.getTitle() != null) {
                object.setText(recognition.getTitle());
            } else {
                return;
            }
            if (recognition.getConfidence() != null) {
                confidence.setText(
                        String.format("%.2f", (100 * recognition.getConfidence())) + "%");
            } else {
                return;
            }

            if(recognition.getTitle().equals("soccer ball") && recognition.getConfidence() >= 55){
                foundView.setVisibility(View.VISIBLE);
            } else {
                foundView.setVisibility(View.INVISIBLE);
            }
        }
    }
}