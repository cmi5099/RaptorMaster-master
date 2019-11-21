package org.tensorflow.lite.examples.classification.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatSeekBar;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import org.tensorflow.lite.examples.classification.R;

public class NewMissionDialogFragment extends DialogFragment implements View.OnClickListener, AppCompatSeekBar.OnSeekBarChangeListener {

    private OnFragmentInteractionListener mListener;
    private NewMissionViewModel mViewModel;

    private TextView objectsTV;
    private TextView durationTV;


    public NewMissionDialogFragment() {
        // Required empty public constructor
    }

    public static NewMissionDialogFragment newInstance() {
        return new NewMissionDialogFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_new_mission_dialog, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ((AppCompatSeekBar) view.findViewById(R.id.objectSB)).setOnSeekBarChangeListener(this);
        ((AppCompatSeekBar) view.findViewById(R.id.durationSB)).setOnSeekBarChangeListener(this);
        view.findViewById(R.id.start).setOnClickListener(this);

        objectsTV = view.findViewById(R.id.label1);
        durationTV = view.findViewById(R.id.label2);

        mViewModel = ViewModelProviders.of(this).get(NewMissionViewModel.class);
        mViewModel.getObjectString().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                objectsTV.setText(s);
            }
        });

        mViewModel.getDurationString().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                durationTV.setText(s);
            }
        });
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
    public void onStart() {
        super.onStart();
        getDialog().getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
        switch (seekBar.getId()) {
            case R.id.objectSB:
                mViewModel.onObjectsChanged(i);
                break;
            case R.id.durationSB:
                mViewModel.onDurationChanged(i);
                break;
        }
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onClick(View view) {
        if (mListener != null) {
            mListener.onStartMission(mViewModel.getmObjects(), mViewModel.getmDuration());
        }
        dismiss();
    }

    public interface OnFragmentInteractionListener {
        void onStartMission(int objectCount, int minutes);
    }
}
