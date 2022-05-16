package com.example.interface1.ui.atm_dav;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class AtmDavViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public AtmDavViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("");
    }

    public LiveData<String> getText() {
        return mText;
    }
}