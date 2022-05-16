package com.example.interface1.ui.smoke;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class SmokeViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public SmokeViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Это дым fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}