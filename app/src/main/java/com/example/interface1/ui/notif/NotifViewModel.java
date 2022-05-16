package com.example.interface1.ui.notif;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class NotifViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public NotifViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Это уведомление fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}