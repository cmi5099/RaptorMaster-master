package org.tensorflow.lite.examples.classification.fragments;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import org.tensorflow.lite.examples.classification.R;

public class NewMissionViewModel extends AndroidViewModel {

    private int mObjects = 2;
    private int mDuration = 15;

    private MutableLiveData<String> mLabel1;
    private MutableLiveData<String> mLabel2;

    public NewMissionViewModel(Application application) {
        super(application);
        mLabel1 = new MutableLiveData<>();
        mLabel2 = new MutableLiveData<>();

        mLabel2.setValue(getApplication().getString(R.string.mission_duration, mDuration));
        mLabel1.setValue(getApplication().getString(R.string.findNObjects, mObjects));
    }

    public LiveData<String> getObjectString() {
        return mLabel1;
    }


    public LiveData<String> getDurationString() {
        return mLabel2;
    }

    public int getmObjects() {
        return mObjects;
    }

    public int getmDuration() {
        return mDuration;
    }

    public void onDurationChanged(int duration) {
        mDuration = duration;
        mLabel2.setValue(getApplication().getString(R.string.mission_duration, mDuration));
    }

    public void onObjectsChanged(int objects) {
        mObjects = objects;
        mLabel1.setValue(getApplication().getString(R.string.findNObjects, mObjects));
    }
}