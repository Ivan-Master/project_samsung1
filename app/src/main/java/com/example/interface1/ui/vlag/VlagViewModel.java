package com.example.interface1.ui.vlag;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class VlagViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public VlagViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Это влажность fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}