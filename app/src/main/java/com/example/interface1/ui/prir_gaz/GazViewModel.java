package com.example.interface1.ui.prir_gaz;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class GazViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public GazViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("");
    }

    public LiveData<String> getText() {
        return mText;
    }
}