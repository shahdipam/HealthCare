package com.example.healthcare.ui.dietplan;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class DietplanViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public DietplanViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is dietplan fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}